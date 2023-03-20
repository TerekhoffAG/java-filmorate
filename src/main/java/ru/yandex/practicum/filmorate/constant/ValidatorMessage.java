package ru.yandex.practicum.filmorate.constant;

public final class ValidatorMessage {
    public static final String RELEASE_DATE = "Дата релиза фильма раньше 1895-12-28";
    public static final String DEFAULT_FIELDS_USER = "DefaultFieldsUser.invalid";
    public static final String EMAIL = "должно иметь формат адреса электронной почты";
    public static final String NOT_BLANK = "не должно быть пустым";
    public static final String POSITIVE = "должно быть больше 0";
    public static final String NO_SPACE = "не должно быть пробелов";
    public static final String PAST_DATE = "дата должна быть в прошлом или в настоящем";
    public static final String MAX_LENGTH_TEXT = "размер должен находиться в диапазоне от 0 до 200";
}
