package ru.yandex.practicum.filmorate.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceInMemory implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceInMemory.class);

    private final Map<Integer, User> users = new HashMap<>();
    private static int idCounter = 0;

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

    private int generateId() {
        return ++idCounter;
    }
}
