package ru.yandex.practicum.filmorate.service.impl;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.exception.NoSuchUserException;
import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FriendService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String INIT_STATUS = "ADD";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserStorage userStorage;
    private final FriendService friendService;

    @Override
    public Optional<User> findById(long id) {
        return userStorage.findById(id);
    }

    @Override
    public void addFriend(User user, User friend) {
        Friend friend1 = buildFriend(null, user.getId(), friend.getId(), INIT_STATUS);
        friendService.save(friend1);
        log.info("Пользователю - " + friend1.getUserId() + " добавили друга - " + friend1.getFriendId());
    }

    @Override
    public void deleteFriend(User user, User friend) {
        friendService.delete(buildFriend(null, user.getId(), friend.getId(), null));
        log.info("Пользователю - " + user.getId() + " удалили друга - " + friend.getId());
    }

    @Override
    public List<Friend> getFriendByUser(User user) {
        return friendService.findAllByUserId(user.getId());
    }

    @Override
    public List<User> getMutualFriends(User user, User friend) {
        List<Long> userFriends = friendService.findAllByUserId(user.getId())
                .stream()
                .map(Friend::getFriendId)
                .collect(Collectors.toList());

        List<Long> friendFriends = friendService.findAllByUserId(friend.getId())
                .stream()
                .map(Friend::getFriendId)
                .collect(Collectors.toList());

        Set<User> mutualFriends = new HashSet<>();

        for (Long friend1 : userFriends) {
            if (friendFriends.contains(friend1)) {
                User mutualUser = findById(friend1).orElseThrow(() -> new NoSuchUserException("No such user with id" + friend1));
                mutualFriends.add(mutualUser);
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

    private Friend buildFriend(Long id, long userId, long friendId, String status) {
        Friend friend = new Friend();
        friend.setId(id);
        friend.setUserId(userId);
        friend.setFriendId(friendId);
        friend.setStatus(status);
        return friend;
    }
}
