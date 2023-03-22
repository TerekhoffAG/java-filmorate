package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
public class UserService extends AbstractService<UserStorage, User> {
    public boolean saveFriend(int id, int friendId) {
        if (id != friendId) {
            User user = (User) getModelsById(id);
            if (user != null) {
                boolean res = user.getFriends().add(friendId);
                if (res) {
                    log.info(LogMessage.ADD_FRIEND, friendId);
                }
                return res;
            } else {
                throw new UserNotFoundException(String.format(ExpMessage.NOT_FOUND_USER, id));
            }
        } else {
            throw new RuntimeException(ExpMessage.NOT_ADD_FRIEND_HIMSELF);
        }
    }

    public boolean removeFriend(int id, int friendId) {
        User user = (User) getModelsById(id);
        if (user != null) {
            boolean res = user.getFriends().remove(id);
            if (res) {
                log.info(LogMessage.REMOVE_FRIEND, friendId);
            }
            return res;
        } else {
            throw new UserNotFoundException(String.format(ExpMessage.NOT_FOUND_USER, id));
        }
    }

    public Collection<User> getFriends(int id) {
        User user = (User) getModelsById(id);
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
        User user = (User) getModelsById(id);
        User friend = (User) getModelsById(friendId);
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
