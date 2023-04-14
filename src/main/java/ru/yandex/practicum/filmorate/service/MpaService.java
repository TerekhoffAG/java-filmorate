package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.SubStorage;

@Slf4j
@Service
public class MpaService extends AbstractSubService<Mpa, Integer> {
    protected MpaService(SubStorage<Mpa, Integer> subStorage) {
        super(subStorage);
    }
}
