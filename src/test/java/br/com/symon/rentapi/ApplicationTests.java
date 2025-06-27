package br.com.symon.rentapi;

import br.com.symon.rentapi.model.Item;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void shouldCreateItemWithoutDetails() throws Exception {

		Item itemToCreate = Item.builder()
				.name("Item Name").build();

		mockMvc.perform(post("/api/items")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(itemToCreate)))
				.andExpect(status().isBadRequest())
				.andReturn();
	}

	@Test
	public void shouldNotCreateItemWithoutName() throws Exception {

		Item itemToCreate = Item.builder()
				.details("Item Details").build();

		MvcResult result = mockMvc.perform(post("/api/items")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(itemToCreate)))
				.andExpect(status().isBadRequest())
				.andReturn();

		String responseBody = result.getResponse().getContentAsString();

		ErrorResponse errorResponse = objectMapper.readValue(responseBody, ErrorResponse.class);

		assertTrue(errorResponse.getErrors().stream().anyMatch(error -> "name".equals(error.field())),
				"Expected at least one error for field 'name'");


	}

	@Test
	public void shouldCreateItemSuccessfully() throws Exception {

		Item itemToCreate = Item.builder()
				.name("Item Name")
				.details("Item Details").build();

		MvcResult result = mockMvc.perform(post("/api/items")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(itemToCreate)))
				.andExpect(status().isCreated())
				.andReturn();

		String responseBody = result.getResponse().getContentAsString();
		Item returnedItem = objectMapper.readValue(responseBody, Item.class);

		assertNotNull(returnedItem.getId(), "Id must not be null");
		assertEquals(itemToCreate.getName(), returnedItem.getName(), "Names must match");
		assertEquals(itemToCreate.getDetails(), returnedItem.getDetails(), "Details must match");

	}

}
