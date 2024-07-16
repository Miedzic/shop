package com.miedzic.shop.validator.impl;

import com.miedzic.shop.domain.dto.ProductDto;
import com.miedzic.shop.validator.ProductValid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ProductNameValidator implements ConstraintValidator<ProductValid, ProductDto> {
    @Override
    public boolean isValid(ProductDto value, ConstraintValidatorContext context) {
        return  !value.getName().equals(value.getCategory());

    }
}
