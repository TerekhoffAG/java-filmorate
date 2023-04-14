package ru.yandex.practicum.filmorate.constant;

public final class UserTable {
    public final static String TABLE_NAME = "user";
    public final static String USER_ID = "id";
    public final static String EMAIL = "email";
    public final static String LOGIN = "login";
    public final static String NAME = "name";
    public final static String BIRTHDAY = "birthday";
    public static final String GET_BY_ID = "select * from user where id = ?";
    public static final String GET_ALL = "select * from user";
    public static final String UPDATE = "update user set email = ?, login = ?, name = ?, birthday = ? where id = ?";
    public static final String DELETE = "delete from user where id = ?";
}
