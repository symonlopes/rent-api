package br.com.symon.rentapi.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConstraintsValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        context.disableDefaultConstraintViolation();

        boolean isValid = true;

        if (password == null || password.isBlank()) {
            context.buildConstraintViolationWithTemplate("Password cannot be null or empty.")
                    .addConstraintViolation();
            isValid = false;
        }

        if(password != null && password.length() < 8 ) {
            context.buildConstraintViolationWithTemplate("Password must be at least 8 characters long.")
                    .addConstraintViolation();
            isValid = false;
        }

        if(password != null && password.length() > 30) {
            context.buildConstraintViolationWithTemplate("Password must be at most 30 characters long.")
                    .addConstraintViolation();
            isValid = false;
        }

        if (password != null && !password.matches(".*[a-z].*")) {
            context.buildConstraintViolationWithTemplate("Password must contain at least one lowercase letter.")
                    .addConstraintViolation();
            isValid = false;
        }

        if (password != null && !password.matches(".*[A-Z].*")) {
            context.buildConstraintViolationWithTemplate("Password must contain at least one uppercase letter.")
                    .addConstraintViolation();
            isValid = false;
        }

        if (password != null && !password.matches(".*[0-9].*")) {
            context.buildConstraintViolationWithTemplate("Password must contain at least one digit.")
                    .addConstraintViolation();
            isValid = false;
        }

        if (password != null && !password.matches(".*[!@#$%^&*()\\-_=+{}\\[\\]:;\"'|\\\\<>,.?/~`].*")) {
            context.buildConstraintViolationWithTemplate("Password must contain at least one special character.")
                    .addConstraintViolation();
            isValid = false;
        }

        if (password != null && password.matches(".*\\s.*")) {
            context.buildConstraintViolationWithTemplate("Password must not contain whitespace characters.")
                    .addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }

}
