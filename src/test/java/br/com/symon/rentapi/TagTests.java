package br.com.symon.rentapi;

import br.com.symon.rentapi.api.ItemApi;
import br.com.symon.rentapi.api.TagApi;
import br.com.symon.rentapi.dto.responses.TagDTO;
import br.com.symon.rentapi.model.Item;
import br.com.symon.rentapi.model.Tag;
import br.com.symon.rentapi.model.TagRef;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
public class TagTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private TestUtils utils;
	@Autowired
	private ItemApi itemApi;
	@Autowired
	private TagApi tagApi;

	@Test
	public void shouldCreateATagSuccessfully() throws Exception {

		var entity = tagApi.createValidTag();

		MvcResult result = mockMvc.perform(post("/api/tag")
						.header("Authorization", "Bearer " + utils.createAdminJwtToken())
						.contentType(MediaType.APPLICATION_JSON)
						.content(utils.getObjectMapper().writeValueAsString(entity)))
				.andExpect(status().isCreated())
				.andReturn();

		var returnedItem = utils.parseResponse(result, Tag.class);

		assertNotNull(returnedItem.getId(), "Id must not be null");
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
