package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.DataModel;

import java.util.Collection;

public interface Storage<K, V extends DataModel> {
    V save(V model);

    V update(V model);

    V remove(V model);

    V findOne(K key);

    Collection<V> findAll();
}
