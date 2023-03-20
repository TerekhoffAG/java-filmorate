package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.annotation.ReleaseDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ReleaseDateValidator implements ConstraintValidator<ReleaseDate, LocalDate> {
    private static final LocalDate MIN_DATE = LocalDate.of(1895, 12, 28);

    @Override
    public void initialize(ReleaseDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext cxt) {
        return value != null && value.isAfter(MIN_DATE);
    }
}
