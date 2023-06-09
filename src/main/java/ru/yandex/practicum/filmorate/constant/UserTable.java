package ru.yandex.practicum.filmorate.constant;

public final class UserTable {
    public static final String TABLE_NAME = "users";
    public static final String USER_ID = "id";
    public static final String EMAIL = "email";
    public static final String LOGIN = "login";
    public static final String NAME = "name";
    public static final String BIRTHDAY = "birthday";
    public static final String GET_BY_ID = "select * from users where id = ?";
    public static final String GET_ALL = "select * from users";
    public static final String UPDATE = "update users set email = ?, login = ?, name = ?, birthday = ? where id = ?";
    public static final String DELETE = "delete from users where id = ?";
}
