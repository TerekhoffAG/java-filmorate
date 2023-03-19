package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.constant.LogMessage;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/users")
public class UserController extends AbstractRestController<User> {
    @PostMapping()
    public User create(@Valid @RequestBody User user) {
        log.info(LogMessage.POST_REQUEST);
        return createModel(user, LogMessage.CREATE_USER);
    }

    @PutMapping()
    public User update(@Valid @RequestBody User user) {
        log.info(LogMessage.PUT_REQUEST);
        return updateModel(user, LogMessage.UPDATE_USER, LogMessage.NOT_FOUND_USER);
    }

    @GetMapping
    public List<User> getAll() {
        log.info(LogMessage.GET_REQUEST);
        return super.getAllModels();
    }
}
