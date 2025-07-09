package br.com.symon.rentapi;

import br.com.symon.rentapi.model.Role;
import br.com.symon.rentapi.model.User;
import br.com.symon.rentapi.service.TokenService;
import br.com.symon.rentapi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
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

    @PostConstruct
    public void postConstruct() {

        var adminUser = User.builder().email("admin@email.com").name("Admin User").build();

        adminUser.getRoles().add(Role.builder().description("CUSTOMER").build());
        adminUser.getRoles().add(Role.builder().description("ADMIN").build());

        userService.registerNewUser(adminUser, "tempTestUser@");

        var customerUser = User.builder().email("customer@email.com").name("Customer User").build();

        customerUser.getRoles().add(Role.builder().description("CUSTOMER").build());
        customerUser.getRoles().add(Role.builder().description("ADMIN").build());

        userService.registerNewUser(customerUser, "tempTestUser@");


    }

    public String createAdminJwtToken() {
        return tokenService.generateToken("admin@email.com","tempTestUser@");
    }

    public String createCustomerJwtToken() {
        return tokenService.generateToken("customer@email.com","tempTestUser@");
    }

}
