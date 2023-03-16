package ru.yandex.practicum.filmorate.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = DefaultFieldsUserValidator.class)
public @interface DefaultFieldsUser {
    String message() default "{DefaultName.invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
