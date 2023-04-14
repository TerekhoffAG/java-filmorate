package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.constant.ValidatorMessage;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.DefaultFieldsUserValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorUserTest {
    private Validator validator;
    private User user;

    @BeforeEach
    void beforeEach() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        user = new User(1, "dolore", "Nick Name", "mail@mail.ru", LocalDate.of(1946, 8, 20));
    }

    @Test
    public void shouldValidUser() {
        Set<ConstraintViolation<User>> actual = validator.validate(user);

        assertTrue(actual.isEmpty());
    }

    @Test
    public void shouldInvalidLoginFieldWithSpace() {
        user.setLogin("dolore ullamco");
        Set<ConstraintViolation<User>> actual = validator.validate(user);

        assertEquals(ValidatorMessage.NO_SPACE, actual.stream().iterator().next().getMessage());
    }

    @Test
    public void shouldInvalidLoginFieldNull() {
        user.setLogin(null);
        Set<ConstraintViolation<User>> actual = validator.validate(user);

        assertEquals(ValidatorMessage.NOT_BLANK, actual.stream().iterator().next().getMessage());
    }

    @Test
    public void shouldInvalidLoginFieldEmpty() {
        user.setLogin("");
        Set<ConstraintViolation<User>> actual = validator.validate(user);

        assertEquals(ValidatorMessage.NOT_BLANK, actual.stream().iterator().next().getMessage());
    }

    @Test
    public void shouldInvalidEmailField() {
        user.setEmail("mail.ru");
        Set<ConstraintViolation<User>> actual = validator.validate(user);

        assertEquals(ValidatorMessage.EMAIL, actual.stream().iterator().next().getMessage());
    }

    @Test
    public void shouldInvalidEmailFieldEmpty() {
        user.setEmail("");
        Set<ConstraintViolation<User>> actual = validator.validate(user);

        assertEquals(ValidatorMessage.NOT_BLANK, actual.stream().iterator().next().getMessage());
    }

    @Test
    public void shouldInvalidBirthdayField() {
        user.setBirthday(LocalDate.of(2246, 8, 20));
        Set<ConstraintViolation<User>> actual = validator.validate(user);

        assertEquals(ValidatorMessage.PAST_DATE, actual.stream().iterator().next().getMessage());
    }

    @Test
    public void shouldInvalidNameFieldNull() {
        user.setName(null);
        new DefaultFieldsUserValidator().isValid(user, null);

        assertEquals(user.getLogin(), user.getName());
    }
}
