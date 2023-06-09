package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.constant.LogMessage;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    @PostMapping()
    public User create(@Valid @RequestBody User user) {
        log.info(LogMessage.POST_REQUEST);
        return service.saveModel(user);
    }

    @PutMapping()
    public User update(@Valid @RequestBody User user) {
        log.info(LogMessage.PUT_REQUEST);
        return service.updateModel(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        log.info(LogMessage.GET_REQUEST);
        return service.getModelsById(id);
    }

    @GetMapping
    public Collection<User> getAll() {
        log.info(LogMessage.GET_REQUEST);
        return service.getAllModels();
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info(LogMessage.PUT_REQUEST);
        service.saveFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info(LogMessage.DELETE_REQUEST);
        service.removeFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getUserFriends(@PathVariable Integer id) {
        log.info(LogMessage.GET_REQUEST);
        return service.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        log.info(LogMessage.GET_REQUEST);
        return service.getCommonFriends(id, otherId);
    }
}
