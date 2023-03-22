package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.DataModel;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.Collection;

@Slf4j
public abstract class AbstractService<S extends Storage, M extends DataModel> {
    protected S storage;

    public DataModel saveModel(M model) {
        return storage.save(model);
    }

    public DataModel updateModel(M model) {
        return storage.update(model);
    }

    public DataModel removeModel(M model) {
        storage.remove(model);
        return model;
    }

    public DataModel getModelsById(Integer id) {
        return storage.findOne(id);
    }

    public Collection<M> getAllModels() {
        return storage.findAll();
    }
}
