package ru.yandex.practicum.filmorate.constant;

public final class FilmTable {
    public static final String TABLE_NAME = "film";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String RELEASE_DATE = "release_date";
    public static final String DURATION = "duration";
    public static final String MPA_ID = "mpa_id";
    public static final String TABLE_FILM_MPA = "film_mpa";
    public static final String GENRE_ID = "genre_id";
    public static final String TABLE_FILM_GENRE = "film_genre";
    public static final String FILM_ID = "film_id";
    public static final String GET_BY_ID = "select * from film where id = ?";
    public static final String GET_ALL_BY_ID = "select f.id, f.name, f.description, f.release_date, f.duration, fm.mpa_id, fg.genre_id " +
            "from film as f " +
            "left join film_mpa as fm on f.id = fm.film_id " +
            "left join film_genre as fg on f.id = fg.film_id " +
            "where f.id = ?";
    public static final String GET_ALL = "select f.id as id, f.name, f.description, f.release_date, f.duration, fm.mpa_id, fg.genre_id " +
            "from film as f " +
            "left join film_mpa as fm on f.id = fm.film_id " +
            "left join film_genre as fg on f.id = fg.film_id";
    public static final String DELETE = "delete from film where id = ?";
    public static final String DELETE_FILM_MPA = "delete from film_mpa where film_id = ?";
    public static final String DELETE_FILM_GENRE = "delete from film_genre where film_id = ?";
    public static final String DELETE_FILM_LIKE = "delete from likes where film_id = ?";
    public static final String UPDATE = "update film set name = ?, description = ?, release_date = ?, duration = ? where id = ?";
    public static final String UPDATE_FILM_MPA = "update film_mpa set mpa_id = ? where film_id = ?";
    public static final String UPDATE_FILM_GENRE = "update film_genre set genre_id = ? where film_id = ?";
}
