package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.constant.ExpMessage;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.util.Collection;
import java.util.Map;

import static ru.yandex.practicum.filmorate.constant.UserTable.*;

@Component("userDbStorage")
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User save(User user) {
        int userPK = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(USER_ID)
                .executeAndReturnKey(Map.of(
                        EMAIL, user.getEmail(),
                        LOGIN, user.getLogin(),
                        NAME, user.getName(),
                        BIRTHDAY, Date.valueOf(user.getBirthday())
                )).intValue(); // TODO проверить что возвращает id
        user.setId(userPK);

        return user;
    }

    @Override
    public User update(User user) {
        if (isExists(user.getId())) {
            jdbcTemplate.update(
                UPDATE,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                Date.valueOf(user.getBirthday()),
                user.getId()
            );

            return user;
        } else {
            throw new ObjectNotFoundException(ExpMessage.NOT_FOUND_USER);
        }
    }

    @Override
    public User remove(User user) {
        if (isExists(user.getId())) {
            jdbcTemplate.update(DELETE, user.getId());

            return user;
        } else {
            throw new ObjectNotFoundException(ExpMessage.NOT_FOUND_USER);
        }
    }

    @Override
    public User findOne(Integer id) {
        return jdbcTemplate.queryForObject(GET_BY_ID, userRowMapper(), id);
    }

    @Override
    public Collection<User> findAll() {
        return jdbcTemplate.query(GET_ALL, userRowMapper());
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> new User(
            rs.getInt(USER_ID),
            rs.getString(EMAIL),
            rs.getString(LOGIN),
            rs.getString(NAME),
            rs.getDate(BIRTHDAY).toLocalDate()
        );
    }

    // TODO заменить на метод findOne если не работает
    private boolean isExists(Integer id) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(GET_BY_ID, id);
        return row.next();
    }
}
