package ru.yandex.practicum.filmorate.storage.friend;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Friend;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class FriendDbStorage implements FriendStorage {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final FriendRowMapper friendRowMapper;

    @Override
    public Friend save(Friend friend) {
        String query = "insert into friends(user_id, friend_id, status) values(:user_id, :friend_id, :status)";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("user_id", friend.getUserId());
        mapSqlParameterSource.addValue("friend_id", friend.getFriendId());
        mapSqlParameterSource.addValue("status", friend.getStatus());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(query, mapSqlParameterSource, keyHolder);

        int id = (int) keyHolder.getKey();

        return findById(id).get();
    }

    @Override
    public List<Friend> findAllByUserId(Long userId) {
        String query = "select * from friends where friends.user_id = :user_id";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("user_id", userId);

        return jdbcTemplate.query(query, mapSqlParameterSource, friendRowMapper);
    }

    @Override
    public Friend update(Friend friend) {
        String query = "update friends set user_id = :user_id, friend_id = :friend_id, status = :status where friends.id = :id";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", friend.getId());
        mapSqlParameterSource.addValue("user_id", friend.getUserId());
        mapSqlParameterSource.addValue("friend_id", friend.getFriendId());
        mapSqlParameterSource.addValue("status", friend.getStatus());

        jdbcTemplate.update(query, mapSqlParameterSource);

        return findById(friend.getId()).get();
    }

    @Override
    public void deleteById(Long id) {
        String query = "delete from friends where friends.id = :id";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);

        jdbcTemplate.update(query, mapSqlParameterSource);
    }

    @Override
    public void deleteByUserIdAndFriendId(Long userId, Long friendId) {
        String query = "delete from friends where friends.user_id = :user_id and friends.friend_id = :friend_id";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("user_id", userId);
        mapSqlParameterSource.addValue("friend_id", friendId);

        jdbcTemplate.update(query, mapSqlParameterSource);
    }

    @Override
    public Optional<Friend> findById(long id) {
        String query = "select * from friends where friends.id = :id";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);

        List<Friend> result = jdbcTemplate.query(query, mapSqlParameterSource, friendRowMapper);
        if (result.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(result.get(0));
    }
}
