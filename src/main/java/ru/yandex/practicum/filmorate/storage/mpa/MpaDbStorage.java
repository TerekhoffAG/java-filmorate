package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.SubStorage;

import java.util.Collection;

import static ru.yandex.practicum.filmorate.constant.MpaTable.*;

@Component
@RequiredArgsConstructor
public class MpaDbStorage implements SubStorage<Mpa, Integer> {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Mpa findOne(Integer id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, mpaRowMapper(), id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Collection<Mpa> findAll() {
        return jdbcTemplate.query(GET_ALL, mpaRowMapper());
    }

    private RowMapper<Mpa> mpaRowMapper() {
        return (rs, rowNum) -> new Mpa(
                rs.getInt(MPA_ID),
                rs.getString(NAME)
        );
    }
}
