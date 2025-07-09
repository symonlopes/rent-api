package br.com.symon.rentapi.validation;

import br.com.symon.rentapi.model.UserRegistrationRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordsEqualValidator implements ConstraintValidator<PasswordsEqual, UserRegistrationRequest> {

    @Override
    public boolean isValid(UserRegistrationRequest userRequest, ConstraintValidatorContext context) {
        if (userRequest == null) {
            return true;
        }
        String password = userRequest.getPassword();
        String passwordConfirmation = userRequest.getPasswordConfirmation();

        return password != null && password.equals(passwordConfirmation);
    }
}