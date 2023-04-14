package ru.yandex.practicum.filmorate.model;

public class Genre extends BaseModel {
    private String name = null;

    public Genre(Integer id) {
        super(id);
    }

    public Genre(Integer id, String name) {
        super(id);
        this.name = name;
    }
}
