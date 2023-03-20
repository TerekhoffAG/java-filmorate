package ru.yandex.practicum.filmorate.annotation;

import ru.yandex.practicum.filmorate.constant.ValidatorMessage;
import ru.yandex.practicum.filmorate.validator.ReleaseDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = ReleaseDateValidator.class)
public @interface ReleaseDate {
    String message() default ValidatorMessage.RELEASE_DATE;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
