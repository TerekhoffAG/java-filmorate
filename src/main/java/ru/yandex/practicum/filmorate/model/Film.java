package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.annotation.ReleaseDate;
import ru.yandex.practicum.filmorate.constant.ValidatorMessage;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class Film extends DataModel {
    @NotBlank(message = ValidatorMessage.NOT_BLANK)
    private String name;

    @Size(max = 200, message = ValidatorMessage.MAX_LENGTH_TEXT)
    private String description;

    @ReleaseDate
    private LocalDate releaseDate;

    @Positive(message = ValidatorMessage.POSITIVE)
    private int duration;

    @JsonIgnore
    private final Set<Integer> likes = new HashSet<>();
}