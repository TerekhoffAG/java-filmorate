package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.DefaultFieldsUser;
import ru.yandex.practicum.filmorate.constant.ValidatorMessage;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
@DefaultFieldsUser
public class User {
    private int id;

    @Email(message = ValidatorMessage.EMAIL)
    @NotBlank(message = ValidatorMessage.NOT_BLANK)
    private String email;

    @NotBlank(message = ValidatorMessage.NOT_BLANK)
    @Pattern(regexp = "\\S*", message = ValidatorMessage.NO_SPACE)
    private String login;

    private String name;

    @PastOrPresent(message = ValidatorMessage.PAST_DATE)
    private LocalDate birthday;
}
