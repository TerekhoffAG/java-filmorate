package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FilmDbStorageTest {
    private final FilmDbStorage storage;
    private Film film1;
    private Film film2;

    @BeforeEach
    public void beforeEach() {
        film1 = new Film(
                1,
                "ФильмТест1",
                "Здесь могла быть твоя реклама",
                LocalDate.of(2010, Month.MARCH, 20),
                100,
                new Mpa(1, "G"),
                Collections.singleton(new Genre(1, "name"))
        );
        film2 = new Film(
                2,
                "ФильмТест2",
                "Здесь могла быть твоя реклама",
                LocalDate.of(2000, Month.MARCH, 10),
                100,
                new Mpa(1, "G"),
                Collections.singleton(new Genre(1, "name"))
        );
        storage.save(film1);
        storage.save(film2);
    }

    @Test
    public void shouldSaveFilm() {
        Film film3 = new Film(
                null,
                "ФильмТест3",
                "Здесь могла быть твоя реклама",
                LocalDate.of(2003, Month.MARCH, 11),
                110,
                new Mpa(1, "G"),
                Collections.singleton(new Genre(1, "name"))
        );
        storage.save(film3);
        film3.setId(3);
        Film film = storage.findOne(3);

        assertThat(film.getId()).isEqualTo(film3.getId());
        assertThat(film.getName()).isEqualTo(film3.getName());
        assertThat(film.getDescription()).isEqualTo(film3.getDescription());
        assertThat(film.getReleaseDate()).isEqualTo(film3.getReleaseDate());
        assertThat(film.getDuration()).isEqualTo(film3.getDuration());
        assertThat(film.getMpa()).isEqualTo(film3.getMpa());
    }

    @Test
    public void shouldReturnFilmById() {
        assertThat(storage.findOne(1)).isEqualTo(film1);
    }

    @Test
    public void shouldReturnAllFilms() {
        Collection<Film> films = storage.findAll();

        assertThat(films.contains(film1)).isTrue();
        assertThat(films).hasSize(2);
    }

    @Test
    public void shouldRemoveFilm() {
        storage.remove(film1);

        assertThat(storage.findAll()).hasSize(1);
        assertThat(storage.findOne(2)).isEqualTo(film2);
    }

    @Test
    public void shouldReturnNullFilms() {
        storage.remove(film1);
        storage.remove(film2);

        assertThat(storage.findAll()).hasSize(0);
    }

    @Test
    public void shouldUpdateFilm() {
        film1.setName("qwerty");
        film1.setDescription("asdf ghjkl 1234567890");
        film1.setReleaseDate(LocalDate.of(2003, Month.MARCH, 11));
        film1.setDuration(1);
        film1.setMpa(new Mpa(3, "3"));
        storage.update(film1);
        Film film = storage.findOne(1);

        assertThat(film.getName()).isEqualTo(film1.getName());
        assertThat(film.getDescription()).isEqualTo(film1.getDescription());
        assertThat(film.getReleaseDate()).isEqualTo(film1.getReleaseDate());
        assertThat(film.getDuration()).isEqualTo(film1.getDuration());
        assertThat(film.getMpa()).isEqualTo(film1.getMpa());
    }
}
