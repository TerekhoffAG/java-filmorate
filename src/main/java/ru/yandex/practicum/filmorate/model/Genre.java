package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Genre extends BaseModel {
    private String name;

    public Genre(Integer id) {
        super(id);
    }

    public Genre(Integer id, String name) {
        super(id);
        this.name = name;
    }
}
