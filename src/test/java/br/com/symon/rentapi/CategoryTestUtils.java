package br.com.symon.rentapi;

import br.com.symon.rentapi.model.Category;
import br.com.symon.rentapi.service.TokenService;
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
public class CategoryTestUtils {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private JwtTestUtils jwtTestUtils;
    @Autowired
    private TestUtils testUtils;


    public Category createValidCategory() {
        return Category.builder()
                .name("Category " + System.currentTimeMillis())
                .build();
    }

    public Category createNewCategory() throws Exception {

        var entity = createValidCategory();

        MvcResult result = mockMvc.perform(post("/api/category")
                        .header("Authorization", "Bearer " + jwtTestUtils.createAdminJwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entity)))
                .andExpect(status().isCreated())
                .andReturn();

        return testUtils.parseResponse(result, Category.class);

    }


}
