package br.com.symon.rentapi;

import br.com.symon.rentapi.service.TokenService;
import br.com.symon.rentapi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;

@Getter
@Component
public class TestUtils {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    public <T> T  parseResponse(MvcResult result, Class<T> errorResponseClass) throws Exception {
        return objectMapper.readValue(result.getResponse().getContentAsString(), errorResponseClass);
    }

    public String createAdminJwtToken() {
        return tokenService.generateToken(Constants.ADMIN_EMAIL, Constants.ADMIN_PASSWORD);
    }

    public String createCustomerJwtToken() {
        return tokenService.generateToken(Constants.CUSTOMER_EMAIL,Constants.CUSTOMER_PASSWORD);
    }

}
