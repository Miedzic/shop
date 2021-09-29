package com.miedzic.shop.validator;

import com.miedzic.shop.validator.impl.ProductNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ProductNameValidator.class)
public @interface ProductValid {
    String message() default "Product name cannot be the same as category is ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
