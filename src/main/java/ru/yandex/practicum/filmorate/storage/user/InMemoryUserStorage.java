package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private int idGenerator = 0;

    public User save(User user) {
        int id = ++idGenerator;
        user.setId(id);
        users.put(id, user);
        return user;
    }

    public User update(User user) {
        int id = user.getId();
        if (users.containsKey(id)) {
            users.put(id, user);
            return user;
        } else {
            return null;
        }
    }

    public User remove(User user) {
        return users.remove(user.getId());
    }

    public User findOne(Integer id) {
        return users.getOrDefault(id, null);
    }

    public Collection<User> findAll() {
        return users.values();
    }
}
