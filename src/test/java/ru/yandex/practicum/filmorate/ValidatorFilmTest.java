package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorFilmTest {
    private Validator validator;
    private Film film;

    @BeforeEach
    void beforeEach() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        film = Film.builder()
                .name("nisi eiusmod")
                .description("adipisicing")
                .releaseDate(LocalDate.of(1967, 1, 25))
                .duration(100)
                .build();
    }

    @Test
    public void shouldValidFilm() {
        Set<ConstraintViolation<Film>> actual = validator.validate(film);

        assertTrue(actual.isEmpty());
    }

    @Test
    public void shouldInvalidNameField() {
        film.setName("");
        Set<ConstraintViolation<Film>> actual = validator.validate(film);

        assertEquals("не должно быть пустым", actual.stream().iterator().next().getMessage());
    }

    @Test
    public void shouldInvalidDescriptionField() {
        film.setDescription("Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. Здесь они хотят разыскать господина Огюста Куглова, который задолжал им деньги, а именно 20 миллионов. о Куглов, который за время «своего отсутствия», стал кандидатом Коломбани.");
        Set<ConstraintViolation<Film>> actual = validator.validate(film);

        assertEquals("размер должен находиться в диапазоне от 0 до 200", actual.stream().iterator().next().getMessage());
    }

    @Test
    public void shouldInvalidReleaseDateField() {
        film.setReleaseDate(LocalDate.of(1890, 1, 25));

        Set<ConstraintViolation<Film>> actual = validator.validate(film);

        assertEquals("Дата релиза фильма раньше 1895-12-28", actual.stream().iterator().next().getMessage());
    }

    @Test
    public void shouldInvalidDurationField() {
        film.setDuration(-200);
        Set<ConstraintViolation<Film>> actual = validator.validate(film);

        assertEquals("должно быть больше 0", actual.stream().iterator().next().getMessage());
    }
}
