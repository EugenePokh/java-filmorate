package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User save(User user);

    List<User> findAll();

    User update(User user);

    Optional<User> findById(long id);

    void addFriend(User user, User friend);

    void deleteFriend(User user, User friend);

    List<User> getMutualFriends(User user, User friend);
}
