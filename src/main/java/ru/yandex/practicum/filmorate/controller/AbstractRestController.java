package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.DataModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class AbstractRestController<T extends DataModel> {
    protected final Map<Integer, T> localRepository = new HashMap<>();
    protected int idGenerator = 0;

    public T createModel(T model, String logMessage) {
        model.setId(++idGenerator);
        localRepository.put(model.getId(), model);
        log.info(logMessage, model.getId());

        return model;
    };

    public T updateModel(T model, String logMessage, String expMessage) {
        if (localRepository.containsKey(model.getId())) {
            localRepository.put(model.getId(), model);
            log.info(logMessage, model.getId());
        } else {
            log.warn(expMessage, model.getId());
            throw new NotFoundException(HttpStatus.NOT_FOUND, expMessage + model.getId());
        }

        return model;
    };

    public List<T> getAllModels() {
        return new ArrayList<>(localRepository.values());
    }
}
