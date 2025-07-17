package br.com.symon.rentapi;

import br.com.symon.rentapi.api.CategoryApi;
import br.com.symon.rentapi.api.ItemApi;
import br.com.symon.rentapi.api.TagApi;
import br.com.symon.rentapi.dto.responses.ItemSaveResponseDTO;
import br.com.symon.rentapi.dto.responses.TagDTO;
import br.com.symon.rentapi.error.ErrorResponse;
import br.com.symon.rentapi.model.*;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
public class ItemTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private TestUtils utils;
	@Autowired
	private ItemApi itemApi;
	@Autowired
	private CategoryApi categoryApi;
	@Autowired
	private TagApi tagApi;


	@Test
	public void shouldCreateItemSuccessfully() throws Exception {

		var category = categoryApi.createNewCategory();

		var tag_1 = tagApi.createNewTag();
		var tag_2 = tagApi.createNewTag();

		var item = itemApi.mockItem();

		item.setCategoryId(category.getId());
		item.getTags().add(TagRef.builder().tagId(tag_1.getId()).build());
		item.getTags().add(TagRef.builder().tagId(tag_2.getId()).build());

		MvcResult result = mockMvc.perform(post("/api/items")
						.header("Authorization", "Bearer " + utils.createAdminJwtToken())
						.contentType(MediaType.APPLICATION_JSON)
						.content(utils.getObjectMapper().writeValueAsString(item)))
				.andExpect(status().isCreated())
				.andReturn();

		var returnedItem = utils.parseResponse(result, ItemSaveResponseDTO.class);

		assertNotNull(returnedItem.getId(), "Item IOD must not be null");
		assertEquals(item.getName(), returnedItem.getName(), "Name must match");
		assertEquals(item.getDetails(), returnedItem.getDetails(), "Details must match");
		assertEquals(2, item.getTags().size(), "Item must have two tags");
		assertEquals(category.getId(), item.getCategoryId(), "Category ID must match");


	}

	@Test
	public void shouldNotCreateItemWithoutAtLeastOneImage() throws Exception {

		Item itemToCreate =  itemApi.mockItem();

		itemToCreate.setImages(null);

		var result = mockMvc.perform(post("/api/items")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + utils.createAdminJwtToken())
						.content(utils.getObjectMapper().writeValueAsString(itemToCreate)))
				.andExpect(status().isBadRequest())
				.andReturn();

		var errorResponse = utils.parseResponse(result, ErrorResponse.class);
		log.info("shouldNotCreateItemWithoutAtLeastOneImage errorResponse: {}", errorResponse);

//		assertTrue(utils.hasErrorOnField("images", errorResponse),
//				"Expected at least one error for field 'images'");
	}

	@Test
	public void shouldCreateItemWithoutDetails() throws Exception {

		Item itemToCreate = itemApi.mockItem();

		itemToCreate.setDetails("");

		mockMvc.perform(post("/api/items")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + utils.createAdminJwtToken())
						.content(utils.getObjectMapper().writeValueAsString(itemToCreate)))
				.andExpect(status().isCreated())
				.andReturn();
	}

	@Test
	public void shouldNotCreateItemWithTooShortName() throws Exception {

		Item itemToCreate = itemApi.mockItem();

		itemToCreate.setName("A");

		MvcResult result = mockMvc.perform(post("/api/items")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + utils.createAdminJwtToken())
						.content(utils.getObjectMapper().writeValueAsString(itemToCreate)))
				.andExpect(status().isBadRequest())
				.andReturn();

		var errorResponse = utils.parseResponse(result, ErrorResponse.class);
		log.info("shouldNotCreateItemWithoutTooShortName errorResponse: {}", errorResponse);
	}

	@Test
	public void shouldNotCreateItemWithoutName() throws Exception {

		Item itemToCreate = itemApi.mockItem();

		itemToCreate.setName("");

		MvcResult result = mockMvc.perform(post("/api/items")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + utils.createAdminJwtToken())
						.content(utils.getObjectMapper().writeValueAsString(itemToCreate)))
				.andExpect(status().isBadRequest())
				.andReturn();

		var errorResponse = utils.parseResponse(result, ErrorResponse.class);
		log.info("shouldNotCreateItemWithoutName errorResponse: {}", errorResponse);
	}

	@Test
	public void shouldGetItemSuccessfully() throws Exception {

		Item itemToCreate = itemApi.mockItem();

		MvcResult result = mockMvc.perform(post("/api/items")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + utils.createAdminJwtToken())
						.content(utils.getObjectMapper().writeValueAsString(itemToCreate)))
				.andExpect(status().isCreated())
				.andReturn();

		var createdItem = utils.parseResponse(result, Item.class);

		log.info("Created Item: {}", createdItem);

		MvcResult getResult = mockMvc.perform(
					get("/api/items/" + createdItem.getId())
							.header("Authorization", "Bearer " + utils.createAdminJwtToken())
				)
				.andExpect(status().isOk())
				.andReturn();

		Item fetchedItem = utils.parseResponse(getResult, Item.class);

		log.info("Fetched Item: {}", fetchedItem);

		assertEquals(createdItem.getId(), fetchedItem.getId());
		assertEquals(itemToCreate.getName(), fetchedItem.getName());
		assertEquals(itemToCreate.getDetails(), fetchedItem.getDetails());

	}

	@Test
	public void shouldReturn404WithInvalidId() throws Exception {

		mockMvc.perform(get("/api/items/" + UUID.randomUUID())
						.header("Authorization", "Bearer " + utils.createAdminJwtToken()))
				.andExpect(status().isNotFound())
				.andReturn();

	}

	@Test
	public void shouldDeleteItemSuccessfully() throws Exception {
		log.info("Starting test: shouldDeleteItemSuccessfully");

		Item itemToCreate = itemApi.mockItem();

		MvcResult createResult = mockMvc.perform(post("/api/items")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + utils.createAdminJwtToken())
						.content(utils.getObjectMapper().writeValueAsString(itemToCreate)))
				.andExpect(status().isCreated())
				.andReturn();

		var createdItem = utils.parseResponse(createResult, Item.class);
		log.info("Item created for deletion with ID: {}", createdItem.getId());

		mockMvc.perform(delete("/api/items/" + createdItem.getId()).header("Authorization", "Bearer " + utils.createAdminJwtToken()))
				.andExpect(status().isNoContent());
		log.info("Delete request sent for item ID: {}", createdItem.getId());

		mockMvc.perform(get("/api/items/" + createdItem.getId()).header("Authorization", "Bearer " + utils.createAdminJwtToken()))
				.andExpect(status().isNotFound());
		log.info("Verified that item ID {} is no longer found.", createdItem.getId());
	}

	@Test
	public void shouldReturn404WhenDeletingInvalidItem() throws Exception {
		log.info("Starting test: shouldReturn404WhenDeletingInvalidItem");
		UUID randomId = UUID.randomUUID();
		mockMvc.perform(delete("/api/items/" + randomId).header("Authorization", "Bearer " + utils.createAdminJwtToken()))
				.andExpect(status().isNotFound());
		log.info("Verified that deleting a non-existent item with ID {} returns 404.", randomId);
	}

	@Test
	public void shouldUpdateItemSuccessfully() throws Exception {
		log.info("Starting test: shouldUpdateItemSuccessfully");

		Item initialItem = itemApi.mockItem();

		MvcResult createResult = mockMvc.perform(post("/api/items")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + utils.createAdminJwtToken())
						.content(utils.getObjectMapper().writeValueAsString(initialItem)))
				.andExpect(status().isCreated())
				.andReturn();

		var createdItem = utils.parseResponse(createResult, Item.class);
		log.info("Initial item created: {}", createdItem);

		createdItem.setName("EDITED NAME");
		createdItem.setDetails("EDITED DETAILS");
		createdItem.getImages().add(Image.builder().url("https://new-image.com/second-image.jpg").build());

		MvcResult updateResult = mockMvc.perform(put("/api/items" )
						.contentType(MediaType.APPLICATION_JSON)
						.content(utils.getObjectMapper().writeValueAsString(createdItem))
						.header("Authorization", "Bearer " + utils.createAdminJwtToken()))
				.andExpect(status().isOk())
				.andReturn();

		var updatedItem = utils.parseResponse(updateResult, Item.class);
		log.info("Item updated: {}", updatedItem);


		assertEquals(createdItem.getId(), updatedItem.getId(), "ID should not change");
		assertEquals(createdItem.getName(), updatedItem.getName());
		assertEquals(createdItem.getDetails(), updatedItem.getDetails());
	}


	@Test
	public void shouldCreateAItemWithMultipleTags() throws Exception {

		var tag_1 = tagApi.createNewTag();
		var tag_2 = tagApi.createNewTag();

		var item = itemApi.createNewItem();

		item.getTags().add(TagDTO.builder().id(tag_1.getId()).build());
		item.getTags().add(TagDTO.builder().id(tag_2.getId()).build());

		MvcResult result = mockMvc.perform(post("/api/items")
						.header("Authorization", "Bearer " + utils.createAdminJwtToken())
						.contentType(MediaType.APPLICATION_JSON)
						.content(utils.getObjectMapper().writeValueAsString(item)))
				.andExpect(status().isCreated())
				.andReturn();

		var returnedEntity = utils.parseResponse(result, Item.class);

		assertNotNull(returnedEntity.getId(), "Id must not be null");
		assertEquals(2, returnedEntity.getTags().size(), "Item must have two tags");

	}

}
