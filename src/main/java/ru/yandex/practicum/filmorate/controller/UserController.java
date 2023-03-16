package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.constant.LogMessage;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController extends AbstractRestController<User> {
    @PostMapping()
    public User create(@Valid @RequestBody User user) {
        user.setId(++idGenerator);
        localRepository.put(user.getId(), user);
        log.info(LogMessage.CREATE_USER, user.getId());

        return user;
    }

    @PutMapping()
    public User update(@Valid @RequestBody User user) {
        if (localRepository.containsKey(user.getId())) {
            localRepository.put(user.getId(), user);
            log.info(LogMessage.UPDATE_USER, user.getId());
        } else {
            log.warn(LogMessage.NOT_FOUND_USER, user.getId());
            throw new NotFoundException(HttpStatus.NOT_FOUND, LogMessage.NOT_FOUND_USER + user.getId());
        }

        return user;
    }
}
