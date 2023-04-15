package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.constant.ExpMessage;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.BaseModel;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

import static ru.yandex.practicum.filmorate.constant.FilmTable.*;

@Component("filmDbStorage")
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film save(Film film) {
        BaseModel mpa = film.getMpa();
        Set<BaseModel> genres = film.getGenres();

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
                    .withTableName(TABLE_FILM_MPA)
                    .execute(Map.of(FILM_ID, filmPK, MPA_ID, mpa.getId()));
        }
        if (!genres.isEmpty()) {
            for (BaseModel genre : genres) {
                new SimpleJdbcInsert(jdbcTemplate)
                        .withTableName(TABLE_FILM_GENRE)
                        .execute(Map.of(FILM_ID, filmPK, GENRE_ID, genre.getId()));
            }
        }

        return film;
    }

    @Override
    public Film update(Film film) {
        Integer id = film.getId();
        BaseModel mpa = film.getMpa();
        Set<BaseModel> genres = film.getGenres();

        if (isExists(GET_BY_ID, id)) {
            jdbcTemplate.update(
                    UPDATE,
                    film.getName(),
                    film.getDescription(),
                    film.getReleaseDate(),
                    film.getDuration(),
                    id
            );
            jdbcTemplate.update(DELETE_FILM_MPA, id);
            jdbcTemplate.update(DELETE_FILM_GENRE, id);
            if (mpa != null) {
                jdbcTemplate.update(UPDATE_FILM_MPA, mpa.getId(), id);
            }
            if (!genres.isEmpty()) {
                Set<BaseModel> genresDB = getGenresByFilm(id);
                for (BaseModel genre : genres) {
                    if (!genresDB.contains(genre)) {
                        jdbcTemplate.update(UPDATE_FILM_GENRE, genre.getId(), id);
                    }
                }
            }

            return film;
        } else {
            throw new ObjectNotFoundException(String.format(ExpMessage.NOT_FOUND_FILM, id));
        }
    }

    @Override
    public Film remove(Film film) {
        Integer id = film.getId();
        if (isExists(GET_BY_ID, id)) {
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
        try {
            return jdbcTemplate.queryForObject(GET_ALL_BY_ID, filmRowMapper(), id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Collection<Film> findAll() {
        return jdbcTemplate.query(GET_ALL, filmRowMapper());
    }

    private Set<BaseModel> getGenresByFilm(Integer id) {
        List<BaseModel> genres = jdbcTemplate.query(GET_FILM_GENRES, genreRowMapper(), id);

        return new HashSet<>(genres);
    }

    private RowMapper<Film> filmRowMapper() {
        return (rs, rowNum) -> new Film(
                rs.getInt(ID),
                rs.getString(NAME),
                rs.getString(DESCRIPTION),
                rs.getDate(RELEASE_DATE).toLocalDate(),
                rs.getInt(DURATION),
                new BaseModel(rs.getInt(MPA_ID)),
                getGenresByFilm(rs.getInt(ID))
        );
    }

    private RowMapper<BaseModel> genreRowMapper() {
        return (rs, rowNum) -> new BaseModel(rs.getInt(GENRE_ID));
    }

    private boolean isExists(String sql, Integer id) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(sql, id);
        return row.next();
    }
 }
