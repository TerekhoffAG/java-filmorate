package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.constant.ExpMessage;
import ru.yandex.practicum.filmorate.constant.FilmTable;
import ru.yandex.practicum.filmorate.constant.MpaTable;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.BaseModel;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.*;

import static ru.yandex.practicum.filmorate.constant.FilmTable.*;
import static ru.yandex.practicum.filmorate.constant.LikesTable.*;
import static ru.yandex.practicum.filmorate.constant.UserTable.GET_BY_ID;

@Component("filmDbStorage")
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film save(Film film) {
        Mpa mpa = film.getMpa();
        Set<Genre> genres = film.getGenres();

        int filmId = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(ID)
                .executeAndReturnKey(Map.of(
                        NAME, film.getName(),
                        DESCRIPTION, film.getDescription(),
                        RELEASE_DATE, film.getReleaseDate(),
                        DURATION, film.getDuration()
                )).intValue();
        if (isExists(MpaTable.GET_BY_ID, mpa.getId())) {
            new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName(TABLE_FILM_MPA)
                    .execute(Map.of(FILM_ID, filmId, MPA_ID, mpa.getId()));
        }
        if (!genres.isEmpty()) {
            for (BaseModel genre : genres) {
                new SimpleJdbcInsert(jdbcTemplate)
                        .withTableName(TABLE_FILM_GENRE)
                        .execute(Map.of(FILM_ID, filmId, GENRE_ID, genre.getId()));
            }
        }

        return findOne(filmId);
    }

    @Override
    public Film update(Film film) {
        Integer id = film.getId();
        Mpa mpa = film.getMpa();
        Set<Genre> genres = film.getGenres();

        if (isExists(FilmTable.GET_BY_ID, id)) {
            jdbcTemplate.update(
                    UPDATE,
                    film.getName(),
                    film.getDescription(),
                    film.getReleaseDate(),
                    film.getDuration(),
                    id
            );
            jdbcTemplate.update(DELETE_FILM_GENRE, id);
            if (isExists(MpaTable.GET_BY_ID, mpa.getId())) {
                jdbcTemplate.update(UPDATE_FILM_MPA, mpa.getId(), id);
            }
            if (!genres.isEmpty()) {
                for (BaseModel genre : genres) {
                    new SimpleJdbcInsert(jdbcTemplate)
                            .withTableName(TABLE_FILM_GENRE)
                            .execute(Map.of(FILM_ID, id, GENRE_ID, genre.getId()));
                }
            }

            return findOne(id);
        } else {
            throw new ObjectNotFoundException(String.format(ExpMessage.NOT_FOUND_FILM, id));
        }
    }

    @Override
    public Film remove(Film film) {
        Integer id = film.getId();
        checkFilm(id);
        jdbcTemplate.update(DELETE_FILM_LIKE, id);
        jdbcTemplate.update(DELETE_FILM_MPA, id);
        jdbcTemplate.update(DELETE_FILM_GENRE, id);
        jdbcTemplate.update(DELETE, id);

        return film;
    }

    @Override
    public Film findOne(Integer id) {
        try {
            return jdbcTemplate.queryForObject(GET_FULL_BY_ID, filmRowMapper(), id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Collection<Film> findAll() {
        return jdbcTemplate.query(GET_ALL, filmRowMapper());
    }

    @Override
    public void addLike(Integer userId, Integer filmId) {
        checkFilm(filmId);
        checkUser(userId);
        jdbcTemplate.update(ADD_LIKE, filmId, userId);
    }

    @Override
    public void removeLike(Integer filmId, Integer userId) {
        checkFilm(filmId);
        checkUser(userId);
        jdbcTemplate.update(REMOVE_LIKE, filmId, userId);
    }

    @Override
    public List<Film> getPopularFilms(Integer count) {
        return jdbcTemplate.query(GET_TOP_FILMS, filmRowMapper(), count);
    }

    private Set<Genre> getGenresByFilm(Integer id) {
        List<Genre> genres = jdbcTemplate.query(GET_FILM_GENRES, genreRowMapper(), id);

        return new HashSet<>(genres);
    }

    private RowMapper<Film> filmRowMapper() {
        return (rs, rowNum) -> new Film(
                rs.getInt(ID),
                rs.getString(NAME),
                rs.getString(DESCRIPTION),
                rs.getDate(RELEASE_DATE).toLocalDate(),
                rs.getInt(DURATION),
                new Mpa(rs.getInt(MPA_ID), rs.getString(MPA_NAME)),
                getGenresByFilm(rs.getInt(ID))
        );
    }

    private RowMapper<Genre> genreRowMapper() {
        return (rs, rowNum) -> new Genre(rs.getInt(GENRE_ID), rs.getString(NAME));
    }

    private boolean isExists(String sql, Integer id) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(sql, id);
        return row.next();
    }

    private void checkFilm(Integer filmId) {
        if (!isExists(FilmTable.GET_BY_ID, filmId)) {
            throw new ObjectNotFoundException(String.format(ExpMessage.NOT_FOUND_FILM, filmId));
        }
    }

    private void checkUser(Integer userId) {
        if (!isExists(GET_BY_ID, userId)) {
            throw new ObjectNotFoundException(String.format(ExpMessage.NOT_FOUND_USER));
        }
    }
}
