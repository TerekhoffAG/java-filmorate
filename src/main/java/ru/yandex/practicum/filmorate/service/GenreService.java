package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.SubStorage;

@Service
public class GenreService extends AbstractSubService<Genre, Integer> {
    protected GenreService(SubStorage<Genre, Integer> subStorage) {
        super(subStorage);
    }
}
