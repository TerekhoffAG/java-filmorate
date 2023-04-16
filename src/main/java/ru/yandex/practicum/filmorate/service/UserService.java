package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.constant.ExpMessage;
import ru.yandex.practicum.filmorate.constant.LogMessage;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserHimselfFriendException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;

@Slf4j
@Service
public class UserService extends AbstractModelService<Integer, User> {
    private final UserStorage userStorage;

    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage storage) {
        super(storage);
        this.userStorage = storage;
    }

    public void saveFriend(Integer id, Integer otherId) {
        if (id != otherId) {
            userStorage.saveFriend(id, otherId);

            log.info(String.format(LogMessage.ADD_FRIEND, id, otherId));
        } else {
            throw new UserHimselfFriendException(ExpMessage.NOT_ADD_FRIEND_HIMSELF);
        }
    }

    public void removeFriend(Integer id, Integer otherId) {
        userStorage.removeFriend(id, otherId);

        log.info(String.format(LogMessage.REMOVE_FRIEND, id, otherId));
    }

    public Collection<User> getFriends(Integer id) {
        return userStorage.findFriends(id);
    }

    /**
     * Получение списка общих друзей.
     */
    public Collection<User> getCommonFriends(Integer id, Integer otherId) {
        return userStorage.getCommonFriends(id, otherId);
    }
}
