package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.constant.LogMessage;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    @PostMapping()
    public User create(@Valid @RequestBody User user) {
        log.info(LogMessage.POST_REQUEST);
        return service.saveModel(user, LogMessage.CREATE_USER);
    }

    @PutMapping()
    public User update(@Valid @RequestBody User user) {
        log.info(LogMessage.PUT_REQUEST);
        return service.updateModel(user, LogMessage.UPDATE_USER, LogMessage.NOT_FOUND_USER);
    }

    @GetMapping
    public Collection<User> getAll() {
        log.info(LogMessage.GET_REQUEST);
        return service.getAllModels();
    }
}
