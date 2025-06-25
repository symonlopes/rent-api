package br.com.symon.rentapi;

import br.com.symon.rentapi.controller.ItemController;
import br.com.symon.rentapi.model.Item;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
public class ApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void shouldCreateItemSuccessfully() throws Exception {
		// Arrange (Organização)
		// 1. Criamos os objetos que vamos usar no teste.
		Item itemToCreate = Item.builder()
				.name("Gamer Chair")
				.details("More details about chair").build();

		Item savedItem = Item.builder()
				.name("Gamer Chair")
				.details("More details about chair").build();

		mockMvc.perform(post("/api/items")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(itemToCreate)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").isNotEmpty())
				.andExpect(jsonPath("$.name").value("Gamer Chair"));
	}

}
