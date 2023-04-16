package ru.yandex.practicum.filmorate.constant;

public final class LikesTable {
    public static final String ADD_LIKE = "insert into likes(film_id, user_id) values (?, ?)";
    public static final String REMOVE_LIKE = "delete from likes where film_id = ? and user_id = ?";
    public static final String GET_TOP_FILMS = "select f.id, f.name, f.description, f.release_date, f.duration, fm.mpa_id, m.name as mpa_name, count(l.user_id) as rating " +
            "from film as f " +
            "left join film_mpa as fm on f.id = fm.film_id " +
            "left join mpa as m on m.id = fm.mpa_id " +
            "left join likes as l on f.id = l.film_id " +
            "group by f.film_id " +
            "order by rating desc " +
            "limit ?";
}
