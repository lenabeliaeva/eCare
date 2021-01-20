package com.example.demo.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface ValidPassword {
    String message() default "Пароль должен содержать хотя бы одну цифру, одну строчную букву, одну заглавную и длина должна быть не менее 8 символов";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
