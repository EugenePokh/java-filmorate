package ru.yandex.practicum.filmorate.storage.mpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
class MpaDbStorageTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private MpaDbStorage mpaDbStorage;

    @BeforeEach
    void before() {
        mpaDbStorage = new MpaDbStorage(new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()), new MpaRowMapper());
    }


    @Test
    void findAll() {
        List<Mpa> mpaDbTest = mpaDbStorage.findAll();
        assertEquals(5, mpaDbTest.size());
        List<String> mpaNames = mpaDbTest.stream().map(Mpa::getName).collect(Collectors.toList());
        assertTrue(mpaNames.contains("G"));
        assertTrue(mpaNames.contains("PG-13"));
    }

    @Test
    void findById() {
        Mpa mpa = mpaDbStorage.findById(1).get();

        assertEquals("G", mpa.getName());
    }
}