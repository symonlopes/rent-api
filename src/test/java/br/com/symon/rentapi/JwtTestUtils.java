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

@Getter
@Component
public class JwtTestUtils {

    @Autowired private ObjectMapper objectMapper;
    @Autowired private UserService userService;
    @Autowired private TokenService tokenService;

    public String createAdminJwtToken() {
        return tokenService.generateToken("admin@email.com","tempTestUser@");
    }

    public String createCustomerJwtToken() {
        return tokenService.generateToken("customer@email.com","tempTestUser@");
    }

}
