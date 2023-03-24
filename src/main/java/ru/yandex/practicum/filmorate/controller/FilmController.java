package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.constant.ExpMessage;
import ru.yandex.practicum.filmorate.constant.LogMessage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {
    private final FilmService service;

    @PostMapping()
    public Film create(@Valid @RequestBody Film film) {
        log.info(LogMessage.POST_REQUEST);
        return service.saveModel(film, LogMessage.CREATE_FILM);
    }

    @PutMapping()
    public Film update(@Valid @RequestBody Film film) {
        log.info(LogMessage.PUT_REQUEST);
        return service.updateModel(film, LogMessage.UPDATE_FILM, ExpMessage.NOT_FOUND_FILM);
    }

    @GetMapping
    public Collection<Film> getAll() {
        log.info(LogMessage.GET_REQUEST);
        return service.getAllModels();
    }
}
