package ru.yandex.practicum.filmorate.service.impl;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NoSuchUserException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserStorage userStorage;

    @Override
    public Optional<User> findById(long id) {
        return userStorage.findById(id);
    }

    @Override
    public void addFriend(User user, User friend) {
        Set<Long> userFriends = user.getFriends();
        userFriends.add(friend.getId());
        log.info("Пользователю - " + user.getId() + " добавили друга - " + friend.getId());

        Set<Long> friendFriends = friend.getFriends();
        friendFriends.add(user.getId());
        log.info("Пользователю - " + friend.getId() + " добавили друга - " + user.getId());
    }

    @Override
    public void deleteFriend(User user, User friend) {
        Set<Long> userFriends = user.getFriends();
        userFriends.remove(friend.getId());
        log.info("Пользователю - " + user.getId() + " удалили друга - " + friend.getId());

        Set<Long> friendFriends = friend.getFriends();
        friendFriends.remove(user.getId());
        log.info("Пользователю - " + friend.getId() + " удалили друга - " + user.getId());
    }

    @Override
    public List<User> getMutualFriends(User user, User friend) {
        Set<Long> userFriends = user.getFriends();
        Set<Long> friendFriends = friend.getFriends();
        Set<User> mutualFriends = new HashSet<>();

        for (Long friendId : userFriends) {
            if (friendFriends.contains(friendId)) {
                mutualFriends.add(findById(friendId).orElseThrow(() -> new NoSuchUserException("No such user with id" + friendId)));
            }
        }

        return new ArrayList<>(mutualFriends);
    }

    @Override
    public User save(User user) {
        fillName(user);
        return userStorage.save(user);
    }

    @Override
    public User update(User user) {
        fillName(user);
        return userStorage.update(user);
    }

    @Override
    public List<User> findAll() {
        return userStorage.findAll();
    }

    private void fillName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
