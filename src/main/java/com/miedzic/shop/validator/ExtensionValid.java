package com.miedzic.shop.validator;

import com.miedzic.shop.validator.impl.ExtensionValidator;
import com.miedzic.shop.validator.impl.FirstnameLengthValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExtensionValidator.class)
public @interface ExtensionValid {
    String message() default "Wrong file extension!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
