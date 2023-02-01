package ru.yandex.practicum.filmorate.storage.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final Map<Long, User> users = new HashMap<>();
    private long idCounter = 0;

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public User save(User user) {
        user.setId(generateId());
        users.put(user.getId(), user);
        log.info("User created - " + user);
        return user;
    }

    @Override
    public User update(User user) {
        log.info("User updated - " + user);
        users.put(user.getId(), user);
        return users.get(user.getId());
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    private long generateId() {
        return ++idCounter;
    }
}
