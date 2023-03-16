package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.DefaultFieldsUser;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
@DefaultFieldsUser
public class User {
    private int id;

    @Email(message = "должно иметь формат адреса электронной почты")
    @NotBlank(message = "не должно быть пустым")
    private String email;

    @NotBlank(message = "не должно быть пустым")
    @Pattern(regexp = "\\S*", message = "должно соответствовать \"\\S*\"")
    private String login;

    private String name;

    @Past(message = "должно содержать прошедшую дату")
    private LocalDate birthday;
}
