package br.com.symon.rentapi;

import br.com.symon.rentapi.model.Role;
import br.com.symon.rentapi.model.User;
import br.com.symon.rentapi.service.RoleService;
import br.com.symon.rentapi.service.TokenService;
import br.com.symon.rentapi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Component
@Log4j2
public class SetupTestUtils {

    @Autowired private ObjectMapper objectMapper;
    @Autowired private UserService userService;
    @Autowired private TokenService tokenService;
    @Autowired private RoleService roleService;


    @PostConstruct
    public void postConstruct() {
        var adminUser = User.builder().email("admin@email.com").name("Admin User").build();
        adminUser.getRoles().add(Role.builder().description("CUSTOMER").build());
        adminUser.getRoles().add(Role.builder().description("ADMIN").build());
        userService.registerNewUser(adminUser, "tempTestUser@");

        var customerUser = User.builder().email("customer@email.com").name("Customer User").build();
        customerUser.getRoles().add(Role.builder().description("CUSTOMER").build());
        userService.registerNewUser(customerUser, "tempTestUser@");

    }

}
