package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public abstract class AbstractRestController<T> {
    protected final Map<Integer, T> localRepository = new HashMap<>();
    protected int idGenerator = 0;

    public abstract T create(@Valid @RequestBody T model);

    public abstract T update(@Valid @RequestBody T model);

    @GetMapping
    public List<T> getAll() {
        return new ArrayList<>(localRepository.values());
    }
}
