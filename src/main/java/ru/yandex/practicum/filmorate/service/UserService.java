package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.constant.ExpMessage;
import ru.yandex.practicum.filmorate.constant.LogMessage;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService extends AbstractService<Integer, User> {

    @Autowired
    public UserService(UserStorage storage) {
        super(storage);
    }

    public void saveFriend(int id, int otherUserId) {
        if (id != otherUserId) {
            User user = getModelsById(id);
            User otherUser = getModelsById(otherUserId);
            if (user != null && otherUser != null) {
                user.getFriends().add(otherUserId);
                otherUser.getFriends().add(id);

                log.info(String.format(LogMessage.ADD_FRIEND, id, otherUserId));
            } else {
                String message = String.format(ExpMessage.NOT_FOUND_USER, user == null ? id : otherUserId);
                throw new UserNotFoundException(message);
            }
        } else {
            throw new RuntimeException(ExpMessage.NOT_ADD_FRIEND_HIMSELF);
        }
    }

    public void removeFriend(int id, int otherUserId) {
        User user = getModelsById(id);
        User otherUser = getModelsById(otherUserId);
        if (user != null && otherUser != null) {
            user.getFriends().remove(otherUserId);
            otherUser.getFriends().remove(id);

            log.info(String.format(LogMessage.REMOVE_FRIEND, id, otherUserId));
        } else {
            String message = String.format(ExpMessage.NOT_FOUND_USER, user == null ? id : otherUserId);
            throw new UserNotFoundException(message);
        }
    }

    public Collection<User> getFriends(int id) {
        User user = getModelsById(id);
        if (user != null) {
            Set<Integer> friends = user.getFriends();
            if (!friends.isEmpty()) {
                return user.getFriends().stream()
                        .map(storage::findOne)
                        .collect(Collectors.toList());
            } else {
                return null;
            }
        } else {
            throw new UserNotFoundException(String.format(ExpMessage.NOT_FOUND_USER, id));
        }
    }

    public Collection<User> getMutualFriends(int id, int friendId) {
        User user = getModelsById(id);
        User friend = getModelsById(friendId);
        if (user != null && friend != null) {
            return user.getFriends().stream()
                    .filter(friend.getFriends()::contains)
                    .map(storage::findOne)
                    .collect(Collectors.toList());
        } else {
            throw new UserNotFoundException(String.format(ExpMessage.NOT_FOUND_USER, user == null ? id : friendId));
        }
    }
}
