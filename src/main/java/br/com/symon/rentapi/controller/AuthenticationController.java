package br.com.symon.rentapi.controller;

import br.com.symon.rentapi.model.LoginRequest;
import br.com.symon.rentapi.model.LoginResponse;
import br.com.symon.rentapi.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest req) {
        String token = tokenService.generateToken(req.email(),req.password());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
