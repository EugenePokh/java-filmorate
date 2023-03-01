package ru.yandex.practicum.filmorate.storage.like;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Like;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class LikeDbStorage implements LikeStorage {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final LikeRowMapper likeRowMapper;

    @Override
    public List<Like> findAll() {
        String query = "select * from likes";
        return jdbcTemplate.query(query, likeRowMapper);
    }

    @Override
    public List<Integer> findMostPopularFilms(int limit) {
        String query =  "select f.id from films f left join likes l on f.id = l.film_id group by f.id order by count(l.id) desc limit :limit";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("limit", limit);

        return jdbcTemplate.query(query, mapSqlParameterSource, (rs, rowNum) -> rs.getInt("id"));
    }


    @Override
    public Like save(Like like) {
        String query = "insert into likes(user_id, film_id) values(:user_id, :film_id)";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("user_id", like.getUserId());
        mapSqlParameterSource.addValue("film_id", like.getFilmId());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(query, mapSqlParameterSource, keyHolder);

        int id = (int) keyHolder.getKey();

        return findById(id).get();
    }

    @Override
    public void deleteByUserIdAndFilmId(Long userId, Long filmId) {
        String query = "delete from likes where likes.user_id = :user_id and likes.film_id = :film_id";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("user_id", userId);
        mapSqlParameterSource.addValue("film_id", filmId);

        jdbcTemplate.update(query, mapSqlParameterSource);
    }

    @Override
    public Optional<Like> findById(int id) {
        String query = "select * from likes where likes.id = :id";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);

        List<Like> result = jdbcTemplate.query(query, mapSqlParameterSource, likeRowMapper);
        if (result.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(result.get(0));
    }
}
