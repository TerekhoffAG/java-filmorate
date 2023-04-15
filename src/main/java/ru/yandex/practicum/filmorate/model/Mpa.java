package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mpa extends BaseModel {
    private String name;
//
//    public Mpa(Integer id) {
//        super(id);
//    }

    public Mpa(Integer id, String name) {
        super(id);
        this.name = name;
    }
}
