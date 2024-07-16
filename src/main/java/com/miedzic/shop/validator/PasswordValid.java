package com.miedzic.shop.validator;

import com.miedzic.shop.validator.impl.PasswordValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordValid {
    String message() default "Password and confirmed password are the same";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
