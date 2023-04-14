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

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService extends AbstractModelService<Integer, Film> {
    private final Comparator<Film> sortingComparator = (a, b) -> b.getLikes().size() - a.getLikes().size();

    @Autowired
    public FilmService(@Qualifier("filmDbStorage") FilmStorage storage) {
        super(storage);
    }

    public void saveLike(Integer id, Integer userId) {
        Film film = getModelsById(id);
        checkFilm(film, id);
        boolean res = film.getLikes().add(userId);
        if (res) {
            log.info(LogMessage.ADD_LIKE, userId);
        } else {
            throw new FilmDoubleLikeException(ExpMessage.NOT_ADD_DOUBLE_LIKE);
        }
    }

    public void removeLike(int id, int userId) {
        Film film = getModelsById(id);
        checkFilm(film, id);
        boolean res = film.getLikes().remove(userId);
        if (res) {
            log.info(LogMessage.REMOVE_LIKE, userId);
        } else {
            throw new ObjectNotFoundException(String.format(ExpMessage.NOT_FOUND_LIKE, userId));
        }
    }

    public Collection<Film> getRatingFilms(int count) {
        Collection<Film> films = getAllModels();
        if (!films.isEmpty()) {
            return films.stream()
                    .sorted(sortingComparator)
                    .limit(count)
                    .collect(Collectors.toList());
        } else {
            throw new ObjectNotFoundException(ExpMessage.NOT_FOUND_FILMS);
        }
    }

    private void checkFilm(Film film, Integer filmId) {
        if (film == null) {
            throw new ObjectNotFoundException(String.format(ExpMessage.NOT_FOUND_FILM, filmId));
        }
    }
}
