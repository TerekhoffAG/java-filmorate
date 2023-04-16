package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
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

import static ru.yandex.practicum.filmorate.constant.FriendTable.*;
import static ru.yandex.practicum.filmorate.constant.UserTable.*;

@Slf4j
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
                )).intValue();
        user.setId(userPK);

        return user;
    }

    @Override
    public User update(User user) {
        checkExists(user.getId());
        jdbcTemplate.update(
                UPDATE,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                Date.valueOf(user.getBirthday()),
                user.getId()
        );

        return user;
    }

    @Override
    public User remove(User user) {
        checkExists(user.getId());
        jdbcTemplate.update(DELETE, user.getId());

        return user;
    }

    @Override
    public User findOne(Integer id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, userRowMapper(), id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Collection<User> findAll() {
        return jdbcTemplate.query(GET_ALL, userRowMapper());
    }

    public void saveFriend(Integer id, Integer otherId) {
        checkExists(id);
        checkExists(otherId);
        jdbcTemplate.update(ADD_FRIEND, id, otherId);
    }

    @Override
    public void removeFriend(Integer id, Integer otherId) {
        checkExists(id);
        checkExists(otherId);
        jdbcTemplate.update(REMOVE_FRIEND, id, otherId);
    }

    @Override
    public Collection<User> findFriends(Integer id) {
        checkExists(id);
        return jdbcTemplate.query(GET_FRIENDS, userRowMapper(), id);
    }

    @Override
    public Collection<User> getCommonFriends(Integer id, Integer otherId) {
        checkExists(id);
        checkExists(otherId);
        return jdbcTemplate.query(GET_COMMON_FRIENDS, userRowMapper(), id, otherId);
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

    private void checkExists(Integer id) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(GET_BY_ID, id);
        if (!row.next()) {
            log.warn(ExpMessage.NOT_FOUND_USER);
            throw new ObjectNotFoundException(String.format(ExpMessage.NOT_FOUND_USER));
        }
    }
}
