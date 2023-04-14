package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BaseModel {
    private Integer id;

    public BaseModel(Integer id) {
        this.id = id;
    }
}
