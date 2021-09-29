package com.miedzic.shop.validator;

import com.miedzic.shop.validator.impl.FirstnameLengthValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FirstnameLengthValidator.class)
public @interface FirstnameValid {
    String message() default "Cmon, firstname cannot be That long!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
