package ru.yandex.practicum.filmorate.storage.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
class UserDbStorageTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private UserDbStorage userDbStorage;

    @BeforeEach
    void before() {
        userDbStorage = new UserDbStorage(new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()), new UserRowMapper());
    }

    @Test
    void saveAndFindById() {
        User expected = buildUser(null);
        User actual = userDbStorage.save(expected);

        assertNotNull(actual.getId());
    }

    @Test
    void findAll() {
        User userTest1 = userDbStorage.save(buildUser(null));
        User userTest2 = userDbStorage.save(buildUser(null));

        List<User> filmDbTest = userDbStorage.findAll();
        assertEquals(2, filmDbTest.size());
        assertTrue(filmDbTest.contains(userTest1));
        assertTrue(filmDbTest.contains(userTest2));
    }

    @Test
    void update() {
        User saved = userDbStorage.save(buildUser(null));

        User expectedUpdated = buildUser(saved.getId());
        User actualUpdated = userDbStorage.update(expectedUpdated);
        assertEquals(expectedUpdated, actualUpdated);
    }

    private User buildUser(Long id) {
        User user = new User();
        user.setId(id);
        user.setName(UUID.randomUUID().toString());
        user.setEmail(UUID.randomUUID().toString());
        user.setLogin(UUID.randomUUID().toString());
        user.setBirthday(LocalDate.now());
        return user;
    }
}