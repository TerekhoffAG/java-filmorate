package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.constant.ExpMessage;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.ErrorResponse;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleObjectNotFoundException(final ObjectNotFoundException exp) {
        log.warn(exp.getMessage());
        return new ErrorResponse(exp.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUserHimselfFriendException(final UserHimselfFriendException exp) {
        log.warn(exp.getMessage());
        return new ErrorResponse(exp.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleFilmDoubleLikeException(final FilmDoubleLikeException exp) {
        log.warn(exp.getMessage());
        return new ErrorResponse(exp.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleFilmNotFoundException(final MethodArgumentNotValidException exp) {
        log.warn(exp.getMessage());
        List<String> errors = new ArrayList<>();
        for (FieldError error : exp.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }
        return new ErrorResponse(errors.toString());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(final RuntimeException exp) {
        log.error(exp.getMessage());
        return new ErrorResponse(ExpMessage.INTERNAL_SERVER_ERROR);
    }
}
