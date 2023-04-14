package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.constant.ExpMessage;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;
import java.util.Map;

import static ru.yandex.practicum.filmorate.constant.FilmTable.*;

@Component("filmDbStorage")
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film save(Film film) {
        Mpa mpa = film.getMpa();
        Genre genre = film.getGenre();

        int filmPK = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(ID)
                .executeAndReturnKey(Map.of(
                        NAME, film.getName(),
                        DESCRIPTION, film.getDescription(),
                        RELEASE_DATE, film.getReleaseDate(),
                        DURATION, film.getDuration()
                )).intValue(); // TODO проверить что возвращает id
        film.setId(filmPK);
        if (mpa != null) {
            new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName(TABLE_MPA_NAME)
                    .execute(Map.of(FILM_ID, filmPK, MPA_ID, mpa));
        }
        if (genre != null) {
            new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName(TABLE_GENRE_NAME)
                    .execute(Map.of(FILM_ID, filmPK, GENRE_ID, genre));
        }

        return film;
    }

    @Override
    public Film update(Film film) {
        Integer id = film.getId();
        Mpa mpa = film.getMpa();
        Genre genre = film.getGenre();

        if (isExists(id)) {
            jdbcTemplate.update(
                    UPDATE,
                    film.getName(),
                    film.getDescription(),
                    film.getReleaseDate(),
                    film.getDuration(),
                    id
            );
            if (mpa != null) {
                jdbcTemplate.update(UPDATE_FILM_MPA, mpa, id);
            }
            if (genre != null) {
                jdbcTemplate.update(UPDATE_FILM_GENRE, genre, id);
            }

            return film;
        } else {
            throw new ObjectNotFoundException(String.format(ExpMessage.NOT_FOUND_FILM, id));
        }
    }

    @Override
    public Film remove(Film film) {
        Integer id = film.getId();
        if (isExists(id)) {
            jdbcTemplate.update(DELETE_FILM_LIKE, id);
            jdbcTemplate.update(DELETE_FILM_MPA, id);
            jdbcTemplate.update(DELETE_FILM_GENRE, id);
            jdbcTemplate.update(DELETE, id);

            return film;
        } else {
            throw new ObjectNotFoundException(String.format(ExpMessage.NOT_FOUND_FILM, id));
        }
    }

    @Override
    public Film findOne(Integer id) {
        return jdbcTemplate.queryForObject(GET_ALL_BY_ID, filmRowMapper(), id);
    }

    @Override
    public Collection<Film> findAll() {
        return jdbcTemplate.query(GET_ALL, filmRowMapper());
    }

    private RowMapper<Film> filmRowMapper() {
        return (rs, rowNum) -> new Film(
                rs.getInt(ID),
                rs.getString(NAME),
                rs.getString(DESCRIPTION),
                rs.getDate(RELEASE_DATE).toLocalDate(),
                rs.getInt(DURATION),
                new Mpa(rs.getInt(MPA_ID)),
                new Genre(rs.getInt(GENRE_ID))
        );
    }

    private boolean isExists (Integer id) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(GET_BY_ID, id);
        return row.next();
    }
 }
