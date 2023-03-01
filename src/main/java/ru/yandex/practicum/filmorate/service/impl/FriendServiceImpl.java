package ru.yandex.practicum.filmorate.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.service.FriendService;
import ru.yandex.practicum.filmorate.storage.friend.FriendStorage;

import java.util.List;

@Service
@AllArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendStorage friendStorage;

    @Override
    public Friend save(Friend friend) {
        return friendStorage.save(friend);
    }

    @Override
    public Friend update(Friend friend) {
        return friendStorage.update(friend);
    }

    @Override
    public void delete(Friend friend) {
        friendStorage.deleteByUserIdAndFriendId(friend.getUserId(), friend.getFriendId());
    }

    @Override
    public List<Friend> findAllByUserId(Long userId) {
        return friendStorage.findAllByUserId(userId);
    }

    @Override
    public void deleteById(Long id) {
        friendStorage.deleteById(id);
    }
}
