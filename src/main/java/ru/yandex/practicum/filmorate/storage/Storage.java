package ru.yandex.practicum.filmorate.storage;

import java.util.Collection;

public interface Storage<K, V> {
    V save(V model);

    V update(V model);

    V remove(V model);

    V findOne(K key);

    Collection<V> findAll();
}
