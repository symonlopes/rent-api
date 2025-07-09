package br.com.symon.rentapi.model;

import br.com.symon.rentapi.validation.Password;
import br.com.symon.rentapi.validation.PasswordsEqual;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@PasswordsEqual
public class UserRegistrationRequest {

    @Email(message = "Invalid email.")
    @NotBlank(message = "Email is required.")
    private String email;

    @NotBlank(message = "Name is required.")
    @Size(min = 5, max = 100, message = "Name must have at least 5 and at most 100 characters.")
    private String name;

    @ToString.Exclude
    @Password
    private String password;

    @ToString.Exclude
    private String passwordConfirmation;
}