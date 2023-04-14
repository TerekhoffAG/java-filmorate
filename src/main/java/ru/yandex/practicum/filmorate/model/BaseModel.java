package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseModel {
    private Integer id;

    public BaseModel(Integer id) {
        this.id = id;
    }
}
