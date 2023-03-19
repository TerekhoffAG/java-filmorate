package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.annotation.DefaultFieldsUser;
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
        if (model.getName() == null || model.getName().isEmpty()) {
            model.setName(model.getLogin());
        }

        return true;
    }
}
