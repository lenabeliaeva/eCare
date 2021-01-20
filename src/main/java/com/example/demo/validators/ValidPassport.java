package com.example.demo.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PassportValidator.class)
@Documented
public @interface ValidPassport {
    String message() default "Введите серию и номер в формате 0123 456789";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
