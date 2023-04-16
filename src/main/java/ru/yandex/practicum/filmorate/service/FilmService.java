package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.constant.ExpMessage;
import ru.yandex.practicum.filmorate.constant.LogMessage;
import ru.yandex.practicum.filmorate.exception.FilmDoubleLikeException;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class FilmService extends AbstractModelService<Integer, Film> {
    private final FilmStorage filmStorage;
    private final Comparator<Film> sortingComparator = (a, b) -> b.getLikes().size() - a.getLikes().size();

    @Autowired
    public FilmService(@Qualifier("filmDbStorage") FilmStorage storage) {
        super(storage);
        this.filmStorage = storage;
    }

    public void saveLike(Integer filmId, Integer userId) {
        Integer res = filmStorage.addLike(userId, filmId);
        if (res != null) {
            log.info(LogMessage.ADD_LIKE, userId);
        } else {
            throw new FilmDoubleLikeException(ExpMessage.NOT_ADD_DOUBLE_LIKE);
        }
    }

    public void removeLike(Integer filmId, Integer userId) {
        Integer res = filmStorage.removeLike(filmId, userId);
        if (res != null) {
            log.info(LogMessage.REMOVE_LIKE, userId);
        } else {
            throw new ObjectNotFoundException(String.format(ExpMessage.NOT_FOUND_LIKE, userId));
        }
    }

    public List<Film> getRatingFilms(int count) {
        List<Film> films = filmStorage.getPopularFilms(count);
        if (films.isEmpty()) {
            throw new ObjectNotFoundException(ExpMessage.NOT_FOUND_FILMS);
        }

        return films;
    }
}
