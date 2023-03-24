package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.constant.ExpMessage;
import ru.yandex.practicum.filmorate.constant.LogMessage;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService extends AbstractModelService<Integer, Film> {

    @Autowired
    public FilmService(FilmStorage storage) {
        super(storage);
    }

    public boolean saveLike(int id, int userId) {
        Film film = getModelsById(id);
        if (film != null) {
            boolean res = film.getLikes().add(userId);
            if (res) {
                log.info(LogMessage.ADD_LIKE, userId);
            }
            return res;
        } else {
            throw new FilmNotFoundException(String.format(ExpMessage.NOT_FOUND_FILM, id));
        }
    }

    public boolean removeLike(int id, int userId) {
        Film film = getModelsById(id);
        if (film != null) {
            boolean res = film.getLikes().remove(userId);
            if (res) {
                log.info(LogMessage.REMOVE_LIKE, userId);
            }
            return res;
        } else {
            throw new FilmNotFoundException(String.format(ExpMessage.NOT_FOUND_FILM, id));
        }
    }

    public Collection<Film> getRatingFilms(int count) {
        Collection<Film> films = getAllModels();
        if (!films.isEmpty()) {
            return films.stream()
                    .sorted((a, b) -> b.getLikes().size() - a.getLikes().size())
                    .limit(count)
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
