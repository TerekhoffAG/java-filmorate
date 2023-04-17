package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.yandex.practicum.filmorate.annotation.DefaultFieldsUser;
import ru.yandex.practicum.filmorate.constant.ValidatorMessage;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@DefaultFieldsUser
@EqualsAndHashCode(callSuper = true)
public class User extends BaseModel {
    @Email(message = ValidatorMessage.EMAIL)
    @NotBlank(message = ValidatorMessage.NOT_BLANK)
    private String email;

    @NotBlank(message = ValidatorMessage.NOT_BLANK)
    @Pattern(regexp = "\\S*", message = ValidatorMessage.NO_SPACE)
    private String login;

    private String name;

    @PastOrPresent(message = ValidatorMessage.PAST_DATE)
    private LocalDate birthday;

    public User(
            Integer id,
            String email,
            String login,
            String name,
            LocalDate birthday) {
        super(id);
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
}
