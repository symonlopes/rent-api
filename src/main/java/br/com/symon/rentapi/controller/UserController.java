package br.com.symon.rentapi.controller;

import br.com.symon.rentapi.model.Role;
import br.com.symon.rentapi.model.User;
import br.com.symon.rentapi.model.UserRegistrationRequest;
import br.com.symon.rentapi.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid UserRegistrationRequest request){
        log.info("Registering a new user [{}] ", request);

        var user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .build();

        user.getRoles().add(Role.builder().description("CUSTOMER").build());
        userService.registerNewUser(user, request.getPassword());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
