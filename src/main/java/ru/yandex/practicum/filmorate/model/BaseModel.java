package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class BaseModel {
    private Integer id;

    public BaseModel(Integer id) {
        this.id = id;
    }
}
