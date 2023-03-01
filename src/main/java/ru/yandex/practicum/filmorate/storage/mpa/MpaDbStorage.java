package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component
public class MpaDbStorage implements MpaStorage {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final MpaRowMapper mpaRowMapper;

    @Override
    public List<Mpa> findAll() {
        String query = "select * from mpas";
        return jdbcTemplate.query(query, mpaRowMapper);
    }

    @Override
    public Optional<Mpa> findById(long id) {
        String query = "select * from mpas where mpas.id = :id";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);

        List<Mpa> result = jdbcTemplate.query(query, mapSqlParameterSource, mpaRowMapper);
        if (result.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(result.get(0));
    }
}
