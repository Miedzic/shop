package com.miedzic.shop.validator.impl;

import com.miedzic.shop.domain.dto.UserDto;
import com.miedzic.shop.validator.PasswordValid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValid, UserDto> {
    @Override
    public boolean isValid(UserDto value, ConstraintValidatorContext context) {
        return value.getPassword().equals(value.getConfirmPassword());
    }
}
