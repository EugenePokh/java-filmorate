package ru.yandex.practicum.filmorate.storage.genre;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
class GenreDbStorageTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private GenreDbStorage genreDbStorage;

    @BeforeEach
    void before() {
        genreDbStorage = new GenreDbStorage(new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()), new GenreRowMapper());
    }


    @Test
    void findAll() {
        List<Genre> genreDbTest = genreDbStorage.findAll();
        assertEquals(6, genreDbTest.size());
        List<String> genreNames = genreDbTest.stream().map(Genre::getName).collect(Collectors.toList());
        assertTrue(genreNames.contains("Комедия"));
        assertTrue(genreNames.contains("Боевик"));
    }

    @Test
    void findById() {
        Genre genre = genreDbStorage.findById(1).get();

        assertEquals("Комедия", genre.getName());
    }
}