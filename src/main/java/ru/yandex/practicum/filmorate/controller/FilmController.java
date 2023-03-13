package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private static final LocalDate MIN_DATE = LocalDate.of(1895, 12, 28);
    private final Map<Integer, Film> films = new HashMap<>();
    private int idGenerator = 0;

    @PostMapping()
    public Film createFilm(@Valid @RequestBody Film film) {
        if (film.getReleaseDate().isAfter(MIN_DATE)) {
            film.setId(++idGenerator);
            films.put(film.getId(), film);
            log.info("Создан фильм с id=" + film.getId());
        } else {
            log.warn("Дата релиза фильма раньше 1895-12-28");
            throw new ValidationException(HttpStatus.BAD_REQUEST, "Дата релиза фильма раньше 1895-12-28");
        }

        return film;
    }

    @PutMapping()
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (films.containsKey(film.getId())) {
            if (film.getReleaseDate().isAfter(MIN_DATE)) {
                films.put(film.getId(), film);
                log.info("Обновлён фильм с id=" + film.getId());
            } else {
                log.warn("Дата релиза фильма с id={} раньше 1895-12-28", film.getId());
                throw new ValidationException(HttpStatus.BAD_REQUEST, "Дата релиза фильма раньше 1895-12-28");
            }
        } else {
            log.warn("Не найден фильм с id=" + film.getId());
            throw new NotFoundException(HttpStatus.BAD_REQUEST, "Не найден фильм с id=" + film.getId());
        }

        return film;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }
}
