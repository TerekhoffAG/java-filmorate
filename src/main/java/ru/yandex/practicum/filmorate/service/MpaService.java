package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.SubStorage;

@Service
public class MpaService extends AbstractSubService<Mpa, Integer> {
    protected MpaService(SubStorage<Mpa, Integer> subStorage) {
        super(subStorage);
    }
}
