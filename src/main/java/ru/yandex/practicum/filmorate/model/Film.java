package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.ReleaseDate;
import ru.yandex.practicum.filmorate.constant.ValidatorMessage;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
public class Film {
    private int id;

    @NotBlank(message = ValidatorMessage.NOT_BLANK)
    private String name;

    @Size(max = 200, message = ValidatorMessage.MAX_LENGTH_TEXT)
    private String description;

    @ReleaseDate
    private LocalDate releaseDate;

    @Positive(message = ValidatorMessage.POSITIVE)
    private int duration;
}