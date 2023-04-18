package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.constant.ExpMessage;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.storage.SubStorage;

import java.util.Collection;

@Slf4j
public abstract class AbstractSubService<T, K> {
    protected final SubStorage<T, K> subStorage;

    protected AbstractSubService(SubStorage<T, K> subStorage) {
        this.subStorage = subStorage;
    }

    public T getSubModelById(K id) {
        T res = subStorage.findOne(id);
        checkSubModel(res);
        return res;
    }

    public Collection<T> getAllSubModels() {
        return subStorage.findAll();
    }

    private void checkSubModel(T model) {
        if (model == null) {
            log.warn(ExpMessage.NOT_FOUND_MODEL);
            throw new ObjectNotFoundException(String.format(ExpMessage.NOT_FOUND_MODEL));
        }
    }
}
