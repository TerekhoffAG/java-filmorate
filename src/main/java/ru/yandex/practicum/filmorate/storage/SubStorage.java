package ru.yandex.practicum.filmorate.storage;

import java.util.Collection;

public interface SubStorage<T, K> {
    T findOne(K id);

    Collection<T> findAll();
}
