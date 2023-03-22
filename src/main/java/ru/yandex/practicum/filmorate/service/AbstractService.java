package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.DataModel;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.Collection;

@Slf4j
public abstract class AbstractService<K, M extends DataModel> {
    protected final Storage<K, M> storage;

    public AbstractService(Storage<K, M> storage) {
        this.storage = storage;
    }

    public M saveModel(M model, String logMessage) {
        M res = storage.save(model);
        log.info(logMessage, model.getId());

        return res;
    }

    public M updateModel(M model, String logMessage, String expMessage) {
        M res = storage.update(model);
        if (res == null) {
            log.warn(expMessage, model.getId());
            throw new RuntimeException(String.format(expMessage, model.getId()));
        }
        log.info(logMessage, model.getId());

        return res;
    }

    public M removeModel(M model, String logMessage, String expMessage) {
        M res = storage.remove(model);
        if (res == null) {
            log.warn(expMessage, model.getId());
            throw new RuntimeException(String.format(expMessage, model.getId()));
        }
        log.info(logMessage, model.getId());

        return res;
    }

    public M getModelsById(K id, String logMessage, String expMessage) {
        M res = storage.findOne(id);
        if (res == null) {
            log.warn(logMessage, id);
            throw new RuntimeException(String.format(expMessage, id));
        }

        return res;
    }

    public Collection<M> getAllModels() {
        return storage.findAll();
    }

    protected M getModelsById(K id) {
        return storage.findOne(id);
    }
}
