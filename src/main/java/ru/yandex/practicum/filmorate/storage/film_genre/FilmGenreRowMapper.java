package ru.yandex.practicum.filmorate.storage.film_genre;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.FilmGenre;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FilmGenreRowMapper implements RowMapper<FilmGenre> {

    @Override
    public FilmGenre mapRow(ResultSet rs, int rowNum) throws SQLException {
        FilmGenre filmGenre = new FilmGenre();
        filmGenre.setId(rs.getLong("id"));
        filmGenre.setGenreId(rs.getLong("genre_id"));
        filmGenre.setFilmId(rs.getLong("film_id"));

        return filmGenre;
    }
}
