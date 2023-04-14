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
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService extends AbstractModelService<Integer, User> {

    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage storage) {
        super(storage);
    }

    public void saveFriend(Integer id, Integer otherUserId) {
        if (id != otherUserId) {
            User user = getModelsById(id);
            User otherUser = getModelsById(otherUserId);
            checkUsers(user, otherUser);

            user.getFriends().add(otherUserId);
            otherUser.getFriends().add(id);

            log.info(String.format(LogMessage.ADD_FRIEND, id, otherUserId));
        } else {
            throw new UserHimselfFriendException(ExpMessage.NOT_ADD_FRIEND_HIMSELF);
        }
    }

    public void removeFriend(Integer id, Integer otherUserId) {
        User user = getModelsById(id);
        User otherUser = getModelsById(otherUserId);
        checkUsers(user, otherUser);

        user.getFriends().remove(otherUserId);
        otherUser.getFriends().remove(id);

        log.info(String.format(LogMessage.REMOVE_FRIEND, id, otherUserId));
    }

    public Collection<User> getFriends(Integer id) {
        User user = getModelsById(id);
        if (user != null) {
            Set<Integer> friends = user.getFriends();
            if (!friends.isEmpty()) {
                return user.getFriends().stream()
                        .map(storage::findOne)
                        .collect(Collectors.toList());
            } else {
                throw new ObjectNotFoundException(ExpMessage.NOT_FOUND_FRIENDS_LIST);
            }
        } else {
            throw new ObjectNotFoundException(String.format(ExpMessage.NOT_FOUND_USER));
        }
    }

    /**
     * Получение списка общих друзей.
     */
    public Collection<User> getCommonFriends(int id, int otherId) {
        User user = getModelsById(id);
        User otherUser = getModelsById(otherId);
        checkUsers(user, otherUser);

        return user.getFriends().stream()
                .filter(otherUser.getFriends()::contains)
                .map(storage::findOne)
                .collect(Collectors.toList());
    }

    private void checkUsers(User user, User otherUser) {
        if (user == null || otherUser == null) {
            throw new ObjectNotFoundException(String.format(ExpMessage.NOT_FOUND_USER));
        }
    }
}
