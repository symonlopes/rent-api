package br.com.symon.rentapi.api;

import br.com.symon.rentapi.TestUtils;
import br.com.symon.rentapi.dto.responses.ItemSaveResponseDTO;
import br.com.symon.rentapi.model.Image;
import br.com.symon.rentapi.model.Item;
import br.com.symon.rentapi.service.TokenService;
import br.com.symon.rentapi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Getter
@Component
public class ItemApi {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private CategoryApi categoryApi;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestUtils utils;

    public Item mockItem() {

        var validItem = Item.builder()
                .name("Item Name")
                .details("Item Details")
                .build();

        validItem.getImages().add(Image.builder().url("https://dummyimage.com/600x400/c6c6c6/000000.jpg")
                .build());

        return validItem;
    }

    public ItemSaveResponseDTO createNewItem() throws Exception {

        var category = categoryApi.createNewCategory();

        var item = mockItem();
        item.setCategoryId(category.getId());

        MvcResult result = mockMvc.perform(post("/api/items")
                        .header("Authorization", "Bearer " + utils.createAdminJwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(utils.getObjectMapper().writeValueAsString(item)))
                .andExpect(status().isCreated())
                .andReturn();

        return utils.parseResponse(result, ItemSaveResponseDTO.class);
    }


}
