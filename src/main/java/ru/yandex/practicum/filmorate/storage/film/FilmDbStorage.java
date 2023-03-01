package ru.yandex.practicum.filmorate.storage.film;


import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
@Primary
public class FilmDbStorage implements FilmStorage {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final FilmRowMapper filmRowMapper;

    @Override
    public Film save(Film film) {
        String query = "insert into films(name, description,  rating_id, duration, release_date) values(:name, :description, :rating_id, :duration, :release_date)";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("name", film.getName());
        mapSqlParameterSource.addValue("description", film.getDescription());
        mapSqlParameterSource.addValue("rating_id", film.getRatingId());
        mapSqlParameterSource.addValue("duration", film.getDuration());
        mapSqlParameterSource.addValue("release_date", film.getReleaseDate());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(query, mapSqlParameterSource, keyHolder);
        int id = (int) keyHolder.getKey();

        return findById(id).get();
    }

    @Override
    public List<Film> findAll() {
        String query = "select * from films";
        return jdbcTemplate.query(query, filmRowMapper);
    }

    @Override
    public Film update(Film film) {
        String query = "update films set name = :name, description = :description, rating_id = :rating_id, duration = :duration, release_date = :release_date where films.id = :id";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", film.getId());
        mapSqlParameterSource.addValue("name", film.getName());
        mapSqlParameterSource.addValue("description", film.getDescription());
        mapSqlParameterSource.addValue("rating_id", film.getRatingId());
        mapSqlParameterSource.addValue("duration", film.getDuration());
        mapSqlParameterSource.addValue("release_date", film.getReleaseDate());

        jdbcTemplate.update(query, mapSqlParameterSource);

        return findById(film.getId()).get();
    }

    @Override
    public Optional<Film> findById(long id) {
        String query = "select * from films where films.id = :id";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);

        List<Film> result = jdbcTemplate.query(query, mapSqlParameterSource, filmRowMapper);
        if (result.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(result.get(0));
    }
}
