package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private int idGenerator = 0;

    @PostMapping()
    public Film createFilm(@Valid @RequestBody Film film) {
            film.setId(++idGenerator);
            films.put(film.getId(), film);
            log.info("Создан фильм с id=" + film.getId());
        return film;
    }

    @PutMapping()
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (films.containsKey(film.getId())) {
                films.put(film.getId(), film);
                log.info("Обновлён фильм с id=" + film.getId());
        } else {
            log.warn("Не найден фильм с id=" + film.getId());
            throw new NotFoundException(HttpStatus.NOT_FOUND, "Не найден фильм с id=" + film.getId());
        }

        return film;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }
}
