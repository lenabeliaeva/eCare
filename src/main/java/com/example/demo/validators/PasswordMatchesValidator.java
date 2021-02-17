package com.example.demo.validators;

import com.example.demo.models.Client;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraint) {
    }

    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        Client client = (Client) obj;
        return client.getPassword().equals(client.getPasswordConfirm());
    }
}
