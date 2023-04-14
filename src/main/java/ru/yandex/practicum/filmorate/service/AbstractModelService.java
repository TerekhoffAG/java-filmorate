package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.constant.ExpMessage;
import ru.yandex.practicum.filmorate.constant.LogMessage;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.BaseModel;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.Collection;

@Slf4j
public abstract class AbstractModelService<K, M extends BaseModel> {
    protected final Storage<K, M> storage;

    public AbstractModelService(Storage<K, M> storage) {
        this.storage = storage;
    }

    public M saveModel(M model) {
        M res = storage.save(model);
        log.info(LogMessage.CREATE_MODEL, model.getId());

        return res;
    }

    public M updateModel(M model) {
        M res = storage.update(model);
        checkModel(res);
        log.info(LogMessage.UPDATE_MODEL, model.getId());

        return res;
    }

    public M removeModel(M model) {
        M res = storage.remove(model);
        checkModel(res);
        log.info(LogMessage.REMOVE_MODEL, model.getId());

        return res;
    }

    public M getModelsById(K id) {
        M res = storage.findOne(id);
        checkModel(res);

        return res;
    }

    public Collection<M> getAllModels() {
        return storage.findAll();
    }

    private void checkModel(M model) {
        if (model == null) {
            log.warn(ExpMessage.NOT_FOUND_MODEL);
            throw new ObjectNotFoundException(String.format(ExpMessage.NOT_FOUND_MODEL));
        }
    }
}
