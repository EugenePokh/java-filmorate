package ru.yandex.practicum.filmorate.storage.genre;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component
public class GenreDbStorage implements GenreStorage {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final GenreRowMapper genreRowMapper;

    @Override
    public List<Genre> findAll() {
        String query = "select * from genres";
        return jdbcTemplate.query(query, genreRowMapper);
    }

    @Override
    public Optional<Genre> findById(long id) {
        String query = "select * from genres where genres.id = :id";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);

        List<Genre> result = jdbcTemplate.query(query, mapSqlParameterSource, genreRowMapper);
        if (result.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(result.get(0));
    }
}
