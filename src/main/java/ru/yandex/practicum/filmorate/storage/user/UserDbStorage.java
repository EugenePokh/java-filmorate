package ru.yandex.practicum.filmorate.storage.user;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;


import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component
@Primary
public class UserDbStorage implements UserStorage {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;

    @Override
    public User save(User user) {
        String query = "insert into users(name, email, login, birthday) values(:name, :email, :login, :birthday)";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("name", user.getName());
        mapSqlParameterSource.addValue("email", user.getEmail());
        mapSqlParameterSource.addValue("login", user.getLogin());
        mapSqlParameterSource.addValue("birthday", user.getBirthday());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(query, mapSqlParameterSource, keyHolder);

        int id = (int) keyHolder.getKey();

        return findById(id).get();
    }

    @Override
    public List<User> findAll() {
        String query = "select * from users";
        return jdbcTemplate.query(query, userRowMapper);
    }

    @Override
    public User update(User user) {
        String query = "update users set name = :name, email = :email, login = :login, birthday = :birthday where users.id = :id";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", user.getId());
        mapSqlParameterSource.addValue("name", user.getName());
        mapSqlParameterSource.addValue("email", user.getEmail());
        mapSqlParameterSource.addValue("login", user.getLogin());
        mapSqlParameterSource.addValue("birthday", user.getBirthday());

        jdbcTemplate.update(query, mapSqlParameterSource);

        return findById(user.getId()).get();
    }

    @Override
    public Optional<User> findById(long id) {
        String query = "select * from users where users.id = :id";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);

        List<User> result = jdbcTemplate.query(query, mapSqlParameterSource, userRowMapper);
        if (result.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(result.get(0));
    }
}
