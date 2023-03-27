package ru.yandex.practicum.filmorate.exception;

public class FilmDoubleLikeException extends RuntimeException {
    public FilmDoubleLikeException(String message) {
        super(message);
    }
}
