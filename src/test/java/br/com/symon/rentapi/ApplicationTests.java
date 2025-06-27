package br.com.symon.rentapi;

import br.com.symon.rentapi.model.Item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private TestUtils utils;

	@Test
	public void shouldCreateItemWithoutDetails() throws Exception {

		Item itemToCreate = Item.builder()
				.name("Item Name").build();

		mockMvc.perform(post("/api/items")
						.contentType(MediaType.APPLICATION_JSON)
						.content(utils.getObjectMapper().writeValueAsString(itemToCreate)))
				.andExpect(status().isCreated())
				.andReturn();
	}

	@Test
	public void shouldNotCreateItemWithoutName() throws Exception {

		Item itemToCreate = Item.builder()
				.details("Item Details").build();

		MvcResult result = mockMvc.perform(post("/api/items")
						.contentType(MediaType.APPLICATION_JSON)
						.content(utils.getObjectMapper().writeValueAsString(itemToCreate)))
				.andExpect(status().isBadRequest())
				.andReturn();

		var errorResponse = utils.parseResponse(result, ErrorResponse.class);

		assertTrue(utils.hasErrorOnField("name", errorResponse),
				"Expected at least one error for field 'name'");


	}



	@Test
	public void shouldCreateItemSuccessfully() throws Exception {

		Item itemToCreate = Item.builder()
				.name("Item Name")
				.details("Item Details").build();

		MvcResult result = mockMvc.perform(post("/api/items")
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
	public void shouldCreateAndGetItemSuccessfully() throws Exception {

		Item itemToCreate = Item.builder()
				.name("Item Name")
				.details("Item Details").build();

		MvcResult result = mockMvc.perform(post("/api/items")
						.contentType(MediaType.APPLICATION_JSON)
						.content(utils.getObjectMapper().writeValueAsString(itemToCreate)))
				.andExpect(status().isCreated())
				.andReturn();

		var createdItem = utils.parseResponse(result, Item.class);

		MvcResult getResult = mockMvc.perform(get("/api/items/" + createdItem.getId()))
				.andExpect(status().isOk())
				.andReturn();

		Item fetchedItem = utils.parseResponse(getResult, Item.class);

		assertEquals(createdItem.getId(), fetchedItem.getId());
		assertEquals(itemToCreate.getName(), fetchedItem.getName());
		assertEquals(itemToCreate.getDetails(), fetchedItem.getDetails());

	}

}
