package br.com.symon.rentapi;

import br.com.symon.rentapi.api.CategoryApi;
import br.com.symon.rentapi.api.ItemApi;
import br.com.symon.rentapi.model.Item;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
public class CategoryTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestUtils utils;
    @Autowired
    private CategoryApi categoryApi;
    @Autowired
    private ItemApi itemApi;

    @Test
    public void shouldCreateItemWithACategory() throws Exception {

        var item = itemApi.mockItem();
        var category = categoryApi.createNewCategory();

        item.setCategoryId(category.getId());

        MvcResult result = mockMvc.perform(post("/api/items")
                        .header("Authorization", "Bearer " + utils.createAdminJwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(utils.getObjectMapper().writeValueAsString(item)))
                .andExpect(status().isCreated())
                .andReturn();

        var returned = utils.parseResponse(result, Item.class);

        log.info("shouldCreateItemWithACategory: {}", returned);

    }

}
