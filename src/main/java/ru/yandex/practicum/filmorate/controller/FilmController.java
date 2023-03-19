package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.constant.LogMessage;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController extends AbstractRestController<Film> {
    @PostMapping()
    public Film create(@Valid @RequestBody Film film) {
        log.info(LogMessage.POST_REQUEST);
        return createModel(film, LogMessage.CREATE_FILM);
    }

    @PutMapping()
    public Film update(@Valid @RequestBody Film film) {
        log.info(LogMessage.PUT_REQUEST);
        return updateModel(film, LogMessage.UPDATE_FILM, LogMessage.NOT_FOUND_FILM);
    }

    @GetMapping
    public List<Film> getAll() {
        log.info(LogMessage.GET_REQUEST);
        return super.getAllModels();
    }
}
