package br.com.symon.rentapi;

import br.com.symon.rentapi.model.Image;
import br.com.symon.rentapi.model.Item;
import br.com.symon.rentapi.service.TokenService;
import br.com.symon.rentapi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;

@Getter
@Component
public class ItemTestUtils {

    @Autowired private ObjectMapper objectMapper;
    @Autowired private UserService userService;
    @Autowired private TokenService tokenService;

    public Item createValidItem() {

        var validItem = Item.builder()
                .name("Item Name")
                .details("Item Details")
                .build();

        validItem.getImages().add(Image.builder().url("https://dummyimage.com/600x400/c6c6c6/000000.jpg")
                .build());

        return validItem;
    }

}
