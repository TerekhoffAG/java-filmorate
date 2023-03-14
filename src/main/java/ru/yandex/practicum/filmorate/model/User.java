package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
public class User {
    private int id;

    @Email
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    @Pattern(regexp = "\\S*")
    private String login;

    private String name;

    @Past
    private LocalDate birthday;
}
