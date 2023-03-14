package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;

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
        user = User.builder()
                .login("dolore")
                .name("Nick Name")
                .email("mail@mail.ru")
                .birthday(LocalDate.of(1946, 8, 20))
                .build();
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

        assertEquals("должно соответствовать \"\\S*\"", actual.stream().iterator().next().getMessage());
    }

    @Test
    public void shouldInvalidLoginFieldNull() {
        user.setLogin(null);
        Set<ConstraintViolation<User>> actual = validator.validate(user);

        assertEquals("не должно быть пустым", actual.stream().iterator().next().getMessage());
    }

    @Test
    public void shouldInvalidLoginFieldEmpty() {
        user.setLogin("");
        Set<ConstraintViolation<User>> actual = validator.validate(user);

        assertEquals("не должно быть пустым", actual.stream().iterator().next().getMessage());
    }

    @Test
    public void shouldInvalidEmailField() {
        user.setEmail("mail.ru");
        Set<ConstraintViolation<User>> actual = validator.validate(user);

        assertEquals("должно иметь формат адреса электронной почты", actual.stream().iterator().next().getMessage());
    }

    @Test
    public void shouldInvalidEmailFieldEmpty() {
        user.setEmail("");
        Set<ConstraintViolation<User>> actual = validator.validate(user);

        assertEquals("не должно быть пустым", actual.stream().iterator().next().getMessage());
    }

    @Test
    public void shouldInvalidBirthdayField() {
        user.setBirthday(LocalDate.now());
        Set<ConstraintViolation<User>> actual = validator.validate(user);

        assertEquals("должно содержать прошедшую дату", actual.stream().iterator().next().getMessage());
    }

    @Test
    public void shouldInvalidNameFieldNull() {
        user.setName(null);
        User actualUser = new UserController().createUser(user);

        assertEquals(user.getLogin(), actualUser.getName());
    }
}
