package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.List;

public interface FilmStorage extends Storage<Integer, Film> {
    Integer addLike(Integer userId, Integer filmId);

    Integer removeLike(Integer filmId, Integer userId);

    List<Film> getPopularFilms(Integer count);
}
