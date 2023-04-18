package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int idGenerator = 0;

    public Film save(Film film) {
        int id = ++idGenerator;
        film.setId(id);
        films.put(id, film);
        return film;
    }

    public Film update(Film film) {
        int id = film.getId();
        if (films.containsKey(id)) {
            films.put(id, film);
            return film;
        } else {
            return null;
        }
    }

    public Film remove(Film film) {
        return films.remove(film.getId());
    }

    public Film findOne(Integer id) {
        return films.get(id);
    }

    public Collection<Film> findAll() {
        return films.values();
    }
}
