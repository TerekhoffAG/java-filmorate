package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
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
        return service.saveModel(film);
    }

    @PutMapping()
    public Film update(@Valid @RequestBody Film film) {
        log.info(LogMessage.PUT_REQUEST);
        return service.updateModel(film);
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Integer id) {
        log.info(LogMessage.GET_REQUEST);
        return service.getModelsById(id);
    }

    @GetMapping
    public Collection<Film> getAll() {
        log.info(LogMessage.GET_REQUEST);
        return service.getAllModels();
    }

    @PutMapping("/{id}/like/{userId}")
    public void likeFilm(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info(LogMessage.PUT_REQUEST);
        service.saveLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info(LogMessage.DELETE_REQUEST);
        service.removeLike(id, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(@RequestParam(defaultValue = "10") int count) {
        log.info(LogMessage.GET_REQUEST);
        return service.getRatingFilms(count);
    }
}
