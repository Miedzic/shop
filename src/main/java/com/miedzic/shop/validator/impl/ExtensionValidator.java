package com.miedzic.shop.validator.impl;

import com.miedzic.shop.domain.dto.ProductDto;
import com.miedzic.shop.validator.ExtensionValid;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExtensionValidator implements ConstraintValidator<ExtensionValid, MultipartFile> {


    @Override
    public boolean isValid(final MultipartFile value, final ConstraintValidatorContext context) {
        return value.getOriginalFilename().endsWith("png")||value.getOriginalFilename().endsWith("jpg");
    }
}
