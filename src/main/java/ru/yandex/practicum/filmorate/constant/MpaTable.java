package ru.yandex.practicum.filmorate.constant;

public final class MpaTable {
    public static final String TABLE_NAME = "mpa";
    public static final String MPA_ID = "id";
    public static final String NAME = "name";
    public final static String GET_BY_ID = "select * from mpa where id = ?";
    public final static String GET_ALL = "select * from mpa";
}
