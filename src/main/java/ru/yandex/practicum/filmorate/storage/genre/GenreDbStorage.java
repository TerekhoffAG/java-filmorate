package ru.yandex.practicum.filmorate.storage.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.SubStorage;

import java.util.Collection;

import static ru.yandex.practicum.filmorate.constant.GenreTable.*;

@Component
@RequiredArgsConstructor
public class GenreDbStorage implements SubStorage<Genre, Integer> {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Genre findOne(Integer id) {
        return jdbcTemplate.queryForObject(GET_BY_ID, genreRowMapper(), id);
    }

    @Override
    public Collection<Genre> findAll() {
        return jdbcTemplate.query(GET_ALL, genreRowMapper());
    }

    private RowMapper<Genre> genreRowMapper() {
        return (rs, rowNum) -> new Genre(
                rs.getInt(GENRE_ID),
                rs.getString(NAME)
        );
    }
}
