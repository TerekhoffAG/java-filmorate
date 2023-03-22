package ru.yandex.practicum.filmorate.constant;

public final class LogMessage {
    public static final String CREATE_USER = "Создан пользователь с id = {}";
    public static final String UPDATE_USER = "Обновлён пользователь с id = {}";
    public static final String NOT_FOUND_USER = "Не найден пользователь с id = {}";
    public static final String CREATE_FILM = "Создан фильм с id = {}";
    public static final String UPDATE_FILM = "Обновлён фильм с id = {}";
    public static final String NOT_FOUND_FILM = "Не найден фильм с id = {}";
    public static final String GET_REQUEST = "Получен get запрос.";
    public static final String POST_REQUEST = "Получен post запрос.";
    public static final String PUT_REQUEST = "Получен put запрос.";
    public static final String ADD_FRIEND = "Пользователи id = %d, %d теперь друзья.";
    public static final String REMOVE_FRIEND = "Пользователи id = %d, %d больше не друзья.";
    public static final String ADD_LIKE = "Пользователь с id = {} поставил лайк.";
    public static final String REMOVE_LIKE = "Пользователь с id = {} удалил лайк.";
}
