package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.ReleaseDate;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
public class Film {
    private int id;

    @NotBlank(message = "не должно быть пустым")
    private String name;

    @Size(max = 200, message = "размер должен находиться в диапазоне от 0 до 200")
    private String description;

    @ReleaseDate
    private LocalDate releaseDate;

    @Positive(message = "должно быть больше 0")
    private int duration;
}
