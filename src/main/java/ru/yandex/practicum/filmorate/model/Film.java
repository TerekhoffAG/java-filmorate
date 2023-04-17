package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.yandex.practicum.filmorate.annotation.ReleaseDate;
import ru.yandex.practicum.filmorate.constant.ValidatorMessage;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Film extends BaseModel {
    @NotBlank(message = ValidatorMessage.NOT_BLANK)
    private String name;

    @Size(max = 200, message = ValidatorMessage.MAX_LENGTH_TEXT)
    private String description;

    @ReleaseDate
    private LocalDate releaseDate;

    @Positive(message = ValidatorMessage.POSITIVE)
    private int duration;

    private Mpa mpa;

    private Set<Genre> genres = new HashSet<>();

    public Film(
            Integer id,
            String name,
            String description,
            LocalDate releaseDate,
            int duration,
            Mpa mpa,
            Set<Genre> genres
    ) {
        super(id);
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
        this.genres = genres;
    }

    public Film(
            Integer id,
            String name,
            String description,
            LocalDate releaseDate,
            int duration,
            Mpa mpa
    ) {
        super(id);
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
    }
}