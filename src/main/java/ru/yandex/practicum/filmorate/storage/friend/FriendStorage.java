package ru.yandex.practicum.filmorate.storage.friend;

import ru.yandex.practicum.filmorate.model.Friend;

import java.util.List;
import java.util.Optional;

public interface FriendStorage {
    Friend save(Friend friend);

    Friend update(Friend friend);

    Optional<Friend> findById(long id);

    void deleteById(Long id);

    void deleteByUserIdAndFriendId(Long userId, Long friendId);

    List<Friend> findAllByUserId(Long userId);
}
