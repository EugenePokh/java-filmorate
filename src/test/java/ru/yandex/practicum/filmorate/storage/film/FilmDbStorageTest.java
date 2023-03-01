package ru.yandex.practicum.filmorate.storage.film;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
class FilmDbStorageTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private FilmDbStorage filmDbStorage;

    @BeforeEach
    void before() {
        filmDbStorage = new FilmDbStorage(new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()), new FilmRowMapper());
    }

    @Test
    void saveAndFindById() {
        Film expected = buildFilm(null, 1);
        Film actual = filmDbStorage.save(expected);

        assertNotNull(actual.getId());
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    void findAll() {
        Film filmTest1 = filmDbStorage.save(buildFilm(1L, 1));
        Film filmTest2 = filmDbStorage.save(buildFilm(2L, 1));

        List<Film> filmDbTest = filmDbStorage.findAll();
        assertEquals(2, filmDbTest.size());
        assertTrue(filmDbTest.contains(filmTest1));
        assertTrue(filmDbTest.contains(filmTest2));
    }

    @Test
    void update() {
        Film saved = filmDbStorage.save(buildFilm(null, 1));

        Film expectedUpdated = buildFilm(saved.getId(), 2);
        Film actualUpdated = filmDbStorage.update(expectedUpdated);
        assertEquals(expectedUpdated, actualUpdated);
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