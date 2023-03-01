package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Friend;

import java.util.List;


public interface FriendService {
    Friend save(Friend friend);

    Friend update(Friend friend);

    void delete(Friend friend);

    void deleteById(Long id);

    List<Friend> findAllByUserId(Long id);
}
