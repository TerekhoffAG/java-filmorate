package ru.yandex.practicum.filmorate.constant;

public final class GenreTable {
    public static final String TABLE_NAME = "genre";
    public static final String GENRE_ID = "id";
    public static final String NAME = "name";
    public final static String GET_BY_ID = "select * from genre where id = ?";
    public final static String GET_ALL = "select * from genre";
}
