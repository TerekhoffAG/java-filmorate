package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.annotation.DefaultFieldsUser;
import ru.yandex.practicum.filmorate.constant.ValidatorMessage;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@DefaultFieldsUser
@EqualsAndHashCode(callSuper = true)
public class User extends DataModel {
    @Email(message = ValidatorMessage.EMAIL)
    @NotBlank(message = ValidatorMessage.NOT_BLANK)
    private String email;

    @NotBlank(message = ValidatorMessage.NOT_BLANK)
    @Pattern(regexp = "\\S*", message = ValidatorMessage.NO_SPACE)
    private String login;

    private String name;

    @PastOrPresent(message = ValidatorMessage.PAST_DATE)
    private LocalDate birthday;

    private final Set<Integer> friends = new HashSet<>();
}
