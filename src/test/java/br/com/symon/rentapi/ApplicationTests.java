package br.com.symon.rentapi;

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
public class ApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private TestUtils utils;

	private Item createValidItem() {
		var validItem = Item.builder()
				.name("Item Name")
				.details("Item Details")
				.build();

		validItem.getImages().add(Image.builder().url("https://dummyimage.com/600x400/c6c6c6/000000.jpg")
				.build());

		return validItem;
	}

	@Test
	public void shouldCreateItemSuccessfully() throws Exception {

		Item itemToCreate = createValidItem();

		MvcResult result = mockMvc.perform(post("/api/items")
						.header("Authorization", "Bearer " + utils.createJwtToken())
						.contentType(MediaType.APPLICATION_JSON)
						.content(utils.getObjectMapper().writeValueAsString(itemToCreate)))
				.andExpect(status().isCreated())
				.andReturn();

		var returnedItem = utils.parseResponse(result, Item.class);

		assertNotNull(returnedItem.getId(), "Id must not be null");
		assertEquals(itemToCreate.getName(), returnedItem.getName(), "Names must match");
		assertEquals(itemToCreate.getDetails(), returnedItem.getDetails(), "Details must match");

	}

	@Test
	public void shouldNotCreateItemWithoutAtLeastOneImage() throws Exception {

		Item itemToCreate = createValidItem();

		itemToCreate.setImages(null);

		var result = mockMvc.perform(post("/api/items")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + utils.createJwtToken())
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

		Item itemToCreate = createValidItem();

		itemToCreate.setDetails("");

		mockMvc.perform(post("/api/items")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + utils.createJwtToken())
						.content(utils.getObjectMapper().writeValueAsString(itemToCreate)))
				.andExpect(status().isCreated())
				.andReturn();
	}

	@Test
	public void shouldNotCreateItemWithTooShortName() throws Exception {

		Item itemToCreate = createValidItem();

		itemToCreate.setName("A");

		MvcResult result = mockMvc.perform(post("/api/items")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + utils.createJwtToken())
						.content(utils.getObjectMapper().writeValueAsString(itemToCreate)))
				.andExpect(status().isBadRequest())
				.andReturn();

		var errorResponse = utils.parseResponse(result, ErrorResponse.class);
		log.info("shouldNotCreateItemWithoutTooShortName errorResponse: {}", errorResponse);

//		assertTrue(utils.hasErrorOnField("name", errorResponse),
//				"Expected at least one error for field 'name'");


	}

	@Test
	public void shouldNotCreateItemWithoutName() throws Exception {

		Item itemToCreate = createValidItem();

		itemToCreate.setName("");

		MvcResult result = mockMvc.perform(post("/api/items")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + utils.createJwtToken())
						.content(utils.getObjectMapper().writeValueAsString(itemToCreate)))
				.andExpect(status().isBadRequest())
				.andReturn();

		var errorResponse = utils.parseResponse(result, ErrorResponse.class);
		log.info("shouldNotCreateItemWithoutName errorResponse: {}", errorResponse);
	}

	@Test
	public void shouldGetItemSuccessfully() throws Exception {

		Item itemToCreate = createValidItem();

		MvcResult result = mockMvc.perform(post("/api/items")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + utils.createJwtToken())
						.content(utils.getObjectMapper().writeValueAsString(itemToCreate)))
				.andExpect(status().isCreated())
				.andReturn();

		var createdItem = utils.parseResponse(result, Item.class);

		log.info("Created Item: {}", createdItem);

		MvcResult getResult = mockMvc.perform(
					get("/api/items/" + createdItem.getId())
							.header("Authorization", "Bearer " + utils.createJwtToken())
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

		mockMvc.perform(get("/api/items/" + UUID.randomUUID()).header("Authorization", "Bearer " + utils.createJwtToken()))
				.andExpect(status().isNotFound())
				.andReturn();

	}

	@Test
	public void shouldDeleteItemSuccessfully() throws Exception {
		log.info("Starting test: shouldDeleteItemSuccessfully");

		Item itemToCreate = createValidItem();

		MvcResult createResult = mockMvc.perform(post("/api/items")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + utils.createJwtToken())
						.header("Authorization", "Bearer " + utils.createJwtToken())
						.content(utils.getObjectMapper().writeValueAsString(itemToCreate)))
				.andExpect(status().isCreated())
				.andReturn();

		var createdItem = utils.parseResponse(createResult, Item.class);
		log.info("Item created for deletion with ID: {}", createdItem.getId());

		mockMvc.perform(delete("/api/items/" + createdItem.getId()).header("Authorization", "Bearer " + utils.createJwtToken()))
				.andExpect(status().isNoContent());
		log.info("Delete request sent for item ID: {}", createdItem.getId());

		mockMvc.perform(get("/api/items/" + createdItem.getId()).header("Authorization", "Bearer " + utils.createJwtToken()))
				.andExpect(status().isNotFound());
		log.info("Verified that item ID {} is no longer found.", createdItem.getId());
	}

	@Test
	public void shouldReturn404WhenDeletingInvalidItem() throws Exception {
		log.info("Starting test: shouldReturn404WhenDeletingInvalidItem");
		UUID randomId = UUID.randomUUID();
		mockMvc.perform(delete("/api/items/" + randomId).header("Authorization", "Bearer " + utils.createJwtToken()))
				.andExpect(status().isNotFound());
		log.info("Verified that deleting a non-existent item with ID {} returns 404.", randomId);
	}

	@Test
	public void shouldUpdateItemSuccessfully() throws Exception {
		log.info("Starting test: shouldUpdateItemSuccessfully");

		Item initialItem = createValidItem();

		MvcResult createResult = mockMvc.perform(post("/api/items")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + utils.createJwtToken())
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
						.header("Authorization", "Bearer " + utils.createJwtToken()))
				.andExpect(status().isOk())
				.andReturn();

		var updatedItem = utils.parseResponse(updateResult, Item.class);
		log.info("Item updated: {}", updatedItem);


		assertEquals(createdItem.getId(), updatedItem.getId(), "ID should not change");
		assertEquals(createdItem.getName(), updatedItem.getName());
		assertEquals(createdItem.getDetails(), updatedItem.getDetails());
//		assertEquals(1, updatedItem.getImages().size());
//		assertTrue(updatedItem.getImages().stream().anyMatch(img -> img.getUrl().equals("https://new-image.com/updated.jpg")));
	}

}
