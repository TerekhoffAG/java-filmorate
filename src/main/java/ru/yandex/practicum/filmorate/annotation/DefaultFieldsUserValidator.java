package ru.yandex.practicum.filmorate.annotation;

import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DefaultFieldsUserValidator implements ConstraintValidator<DefaultFieldsUser, User> {
    @Override
    public void initialize(DefaultFieldsUser constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(User model, ConstraintValidatorContext cxt) {
        if (model.getName() == null) {
            model.setName(model.getLogin());
        }
        return true;
    }
}
