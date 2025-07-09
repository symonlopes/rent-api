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

//    public boolean hasErrorOnField(String field, ErrorResponse errorResponse) {
//        return errorResponse.getErrors().stream().anyMatch(
//                error ->
//                        field.equals(error.field()
//                        )
//        );
//    }

    @PostConstruct
    public void postConstruct() {

        var user = User.builder().email("email@email.com").name("Test User").build();

        user.getRoles().add(Role.builder().description("CUSTOMER").build());
        user.getRoles().add(Role.builder().description("ADMIN").build());

        userService.registerNewUser(user, "tempTestUser@");

    }

    public String createJwtToken() {
        return tokenService.generateToken("email@email.com","tempTestUser@");
    }

}
