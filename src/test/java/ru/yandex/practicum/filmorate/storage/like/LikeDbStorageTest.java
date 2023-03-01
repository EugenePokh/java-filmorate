package ru.yandex.practicum.filmorate.storage.like;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmRowMapper;
import ru.yandex.practicum.filmorate.storage.genre.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreRowMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
class LikeDbStorageTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private LikeDbStorage likeDbStorage;
    private FilmDbStorage filmDbStorage;

    @BeforeEach
    void before() {
        likeDbStorage = new LikeDbStorage(new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()), new LikeRowMapper());
        filmDbStorage = new FilmDbStorage(new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()), new FilmRowMapper());
    }


    @Test
    void findMostPopularFilms() {

        Film film1 = filmDbStorage.save(buildFilm(null, 1));
        Film film2 = filmDbStorage.save(buildFilm(null, 1));
        Film film3 = filmDbStorage.save(buildFilm(null, 1));

        likeDbStorage.save(new Like(1L, film1.getId()));
        likeDbStorage.save(new Like(2L, film1.getId()));
        likeDbStorage.save(new Like(3L, film1.getId()));
        likeDbStorage.save(new Like(1L, film2.getId()));
        likeDbStorage.save(new Like(2L, film2.getId()));
        likeDbStorage.save(new Like(1L, film3.getId()));

        List<Integer> filmIds = likeDbStorage.findMostPopularFilms(3);

        assertEquals(3, filmIds.size());
        assertEquals(film1.getId().intValue(), filmIds.get(0));
        assertEquals(film2.getId().intValue(), filmIds.get(1));
        assertEquals(film3.getId().intValue(), filmIds.get(2));

    }

    private Film buildFilm(Long id, long mpaId) {
        Film film = new Film();
        film.setId(id);
        film.setName(UUID.randomUUID().toString());
        film.setDescription(UUID.randomUUID().toString());
        film.setRatingId(mpaId);
        film.setDuration(1);
        film.setReleaseDate(LocalDate.now());
        return film;
    }
}