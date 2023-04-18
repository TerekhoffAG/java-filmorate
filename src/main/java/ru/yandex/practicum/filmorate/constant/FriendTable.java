package ru.yandex.practicum.filmorate.constant;

public final class FriendTable {
    public static final String ADD_FRIEND = "insert into friend (user_id, friend_id) values (?, ?)";
    public static final String REMOVE_FRIEND = "delete from friend where user_id = ? and friend_id = ?";
    public static final String GET_FRIENDS = "select * " +
            "from users " +
            "where id in (select friend_id from friend where user_id = ?)";
    public static final String GET_COMMON_FRIENDS = "select * " +
            "from users " +
            "where id in (select friend_id " +
            "from friend " +
            "where user_id = ? " +
            "and friend_id in (select friend_id from friend where user_id = ?))";
}
