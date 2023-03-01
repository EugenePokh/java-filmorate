package ru.yandex.practicum.filmorate.storage.film_genre;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.FilmGenre;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class FilmGenreDbStorage implements FilmGenreStorage {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final FilmGenreRowMapper filmGenreRowMapper;

    @Override
    public FilmGenre save(FilmGenre filmGenre) {
        String query = "insert into film_genres(genre_id, film_id) values(:genre_id, :film_id)";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("genre_id", filmGenre.getGenreId());
        mapSqlParameterSource.addValue("film_id", filmGenre.getFilmId());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(query, mapSqlParameterSource, keyHolder);

        int id = (int) keyHolder.getKey();

        return findById(id).get();
    }

    @Override
    public void deleteByFilmId(Long filmId) {
        String query = "delete from film_genres fg where fg.film_id = :film_id";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("film_id", filmId);

        jdbcTemplate.update(query, mapSqlParameterSource);
    }

    @Override
    public List<FilmGenre> findAllByFilmId(Long filmId) {
        String query = "select * from film_genres fg where fg.film_id = :film_id";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("film_id", filmId);

        return jdbcTemplate.query(query, mapSqlParameterSource, filmGenreRowMapper);
    }

    @Override
    public Optional<FilmGenre> findById(int id) {
        String query = "select * from film_genres fg where fg.id = :id";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);

        List<FilmGenre> result = jdbcTemplate.query(query, mapSqlParameterSource, filmGenreRowMapper);
        if (result.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(result.get(0));
    }
}
