package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.constant.LogMessage;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController extends AbstractRestController<Film> {
    @PostMapping()
    public Film create(@Valid @RequestBody Film film) {
        film.setId(++idGenerator);
        localRepository.put(film.getId(), film);
        log.info(LogMessage.CREATE_FILM, film.getId());

        return film;
    }

    @PutMapping()
    public Film update(@Valid @RequestBody Film film) {
        if (localRepository.containsKey(film.getId())) {
                localRepository.put(film.getId(), film);
                log.info(LogMessage.UPDATE_FILM, film.getId());
        } else {
            log.warn(LogMessage.NOT_FOUND_FILM, film.getId());
            throw new NotFoundException(HttpStatus.NOT_FOUND, LogMessage.NOT_FOUND_FILM + film.getId());
        }

        return film;
    }
}
