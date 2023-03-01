package ru.yandex.practicum.filmorate.storage.friend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.yandex.practicum.filmorate.model.Friend;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
class FriendDbStorageTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private FriendDbStorage friendDbStorage;

    @BeforeEach
    void before() {
        friendDbStorage = new FriendDbStorage(new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()), new FriendRowMapper());
    }

    @Test
    void saveAndFindById() {
        Friend expected = buildFriend(null, 1, 1, "yes");
        Friend actual = friendDbStorage.save(expected);

        assertNotNull(actual.getId());
    }

    @Test
    void update() {
        Friend saved = friendDbStorage.save(buildFriend(null, 1, 1, "yes"));

        Friend expectedUpdated = buildFriend(saved.getId(), 2, 2, "no");
        Friend actualUpdated = friendDbStorage.update(expectedUpdated);
        assertEquals(expectedUpdated, actualUpdated);
    }

    @Test
    void deleteByUserIdANdFriendId() {
        Friend expected = buildFriend(null, 1, 1, "yes");
        Friend actual = friendDbStorage.save(expected);
        friendDbStorage.deleteByUserIdAndFriendId(actual.getUserId(), actual.getFriendId());
        Optional<Friend> result = friendDbStorage.findById(actual.getId());

        assertTrue(result.isEmpty());
    }

    @Test
    void findAllByUserId() {
        friendDbStorage.save(buildFriend(null, 1, 1, null));

        friendDbStorage.save(buildFriend(null, 1, 2, null));

        friendDbStorage.save(buildFriend(null, 2, 1, null));

        assertEquals(2, friendDbStorage.findAllByUserId(1L).size());

    }

    @Test
    void deleteAndFindById() {
        Friend expected = buildFriend(null, 1, 1, "yes");
        Friend actual = friendDbStorage.save(expected);
        friendDbStorage.deleteById(actual.getId());
        Optional<Friend> result = friendDbStorage.findById(actual.getId());

        assertTrue(result.isEmpty());
    }

    private Friend buildFriend(Long id, long userId, long friendId, String status) {
        Friend friend = new Friend();
        friend.setId(id);
        friend.setUserId(userId);
        friend.setFriendId(friendId);
        friend.setStatus(status);
        return friend;
    }
}