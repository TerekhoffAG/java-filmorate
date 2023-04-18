package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.Collection;

public interface UserStorage extends Storage<Integer, User> {
    public void saveFriend(Integer id, Integer otherId);

    public void removeFriend(Integer id, Integer otherId);

    public Collection<User> findFriends(Integer id);

    public Collection<User> getCommonFriends(Integer id, Integer otherId);
}
