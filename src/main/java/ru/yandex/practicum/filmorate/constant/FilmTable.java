package ru.yandex.practicum.filmorate.constant;

public final class FilmTable {
    public final static String TABLE_NAME = "film";
    public final static String ID = "id";
    public final static String NAME = "name";
    public final static String DESCRIPTION = "description";
    public final static String RELEASE_DATE = "release_date";
    public final static String DURATION = "duration";
    public final static String MPA_ID = "mpa_id";
    public final static String TABLE_MPA_NAME = "mpa_name";
    public final static String GENRE_ID = "genre_id";
    public final static String TABLE_GENRE_NAME = "genre_name";
    public final static String FILM_ID = "film_id";
    public final static String GET_BY_ID = "select * from film where id = ?";
    public final static String GET_ALL_BY_ID = "select f.id, f.name, f.description, f.release_date, f.duration, fm.mpa_id, fg.genre_id " +
            "from film as f" +
            "left join film_mpa as fm on f.id = fm.id" +
            "left join film_genre as fg on f.id = fg.id" +
            "where f.id = ?";
    public final static String GET_ALL = "select f.id, f.name, f.description, f.release_date, f.duration, fm.mpa_id, fg.genre_id " +
            "from film as f" +
            "left join film_mpa as fm on f.id = fm.id" +
            "left join film_genre as fg on f.id = fg.id";
    public final static String DELETE = "delete from film where id = ?";
    public final static String DELETE_FILM_MPA = "delete from film_mpa where id = ?";
    public final static String DELETE_FILM_GENRE = "delete from film_genre where id = ?";
    public final static String DELETE_FILM_LIKE = "delete from like where id = ?";
    public final static String UPDATE = "update film set name = ?, description = ?, release_date = ?, duration = ? where id = ?";
    public final static String UPDATE_FILM_MPA = "update film_mpa set mpa_id = ? where film_id = ?";
    public final static String UPDATE_FILM_GENRE = "update film_genre set genre_id = ? where film_id = ?";
}
