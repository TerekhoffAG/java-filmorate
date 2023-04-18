package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.constant.LogMessage;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService service;

    @GetMapping("/{id}")
    public Genre getGenre(@PathVariable Integer id) {
        log.info(LogMessage.GET_REQUEST);
        return  service.getSubModelById(id);
    }

    @GetMapping
    public Collection<Genre> getAllGenre() {
        log.info(LogMessage.GET_REQUEST);
        return service.getAllSubModels();
    }
}
