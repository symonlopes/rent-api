package br.com.symon.rentapi.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequest {

    @Email @NotBlank
    private String email;

    @NotBlank
    @Size(min = 5, max = 100, message = "Name must have at least 5 and at most 100 characters.")
    private String name;

    @NotBlank
    @Size(min = 8, max = 30, message = "Password must have at least 8 and at most 30 characters.")
    private String password;

    @NotBlank
    @Size(min = 8, max = 30, message = "Password confirmation must have at least 8 and at most 30 characters.")
    private String passwordConfirmation;
}