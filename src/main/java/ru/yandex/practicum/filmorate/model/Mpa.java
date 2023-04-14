package ru.yandex.practicum.filmorate.model;

public class Mpa extends BaseModel {
    private String name = null;

    public Mpa(Integer id) {
        super(id);
    }

    public Mpa(Integer id, String name) {
        super(id);
        this.name = name;
    }
}
