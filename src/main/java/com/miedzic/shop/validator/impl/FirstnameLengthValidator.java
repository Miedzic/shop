package com.miedzic.shop.validator.impl;

import com.miedzic.shop.domain.dto.UserDto;
import com.miedzic.shop.validator.FirstnameValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FirstnameLengthValidator implements ConstraintValidator<FirstnameValid, UserDto> {
    @Override
    public boolean isValid(UserDto value, ConstraintValidatorContext context) {
        return value.getFirstName().length() < 100;
    }
}
