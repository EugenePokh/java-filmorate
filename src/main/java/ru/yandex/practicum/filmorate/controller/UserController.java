package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controller.dto.UserRequestDto;
import ru.yandex.practicum.filmorate.exception.NoSuchUserException;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@AllArgsConstructor
public class UserController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserMapper userMapper;
    private final UserService userService;

    @PostMapping("/users")
    public User createUser(@Validated @RequestBody UserRequestDto userRequestDto) {
        log.debug("Handle endpoint POST /users dto - " + userRequestDto);
        User user = userMapper.toModel(userRequestDto);
        User created = userService.save(user);

        return created;
    }

    @PutMapping("/users")
    public User updateUser(@Validated @RequestBody UserRequestDto userRequestDto) {
        long id;
        if (Objects.nonNull(userRequestDto.getId())) {
            id = userRequestDto.getId();
            userService.findById(id).orElseThrow(() -> new NoSuchUserException("No such user with id " + id));
        } else {
            throw new ValidationException("Has no id in dto");
        }
        log.debug("Handle endpoint PUT /users/id dto - " + userRequestDto);
        User user = userMapper.toModel(userRequestDto);
        user.setId(id);

        return userService.update(user);
    }

    @GetMapping("/users")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public User getById(@PathVariable Long id) {
        return userService.findById(id).orElseThrow(() -> new NoSuchUserException("No such user with id " + id));
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriends(@PathVariable Long id, @PathVariable Long friendId) {
        User user = userService.findById(id).orElseThrow(() -> new NoSuchUserException("No such user with id " + id));
        User friend = userService.findById(friendId).orElseThrow(() -> new NoSuchUserException("No such user with id " + friendId));
        userService.addFriend(user, friend);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriends(@PathVariable Long id, @PathVariable Long friendId) {
        User user = userService.findById(id).orElseThrow(() -> new NoSuchUserException("No such user with id " + id));
        User friend = userService.findById(friendId).orElseThrow(() -> new NoSuchUserException("No such user with id " + friendId));
        userService.deleteFriend(user, friend);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> getFriends(@PathVariable Long id) {
        User user = userService.findById(id).orElseThrow(() -> new NoSuchUserException("No such user with id " + id));
        Set<Long> ids = user.getFriends();
        List<User> friends = new ArrayList<>();
        for (Long idFriend : ids) {
            User friend = userService.findById(idFriend).orElseThrow(() -> new NoSuchUserException("No such user with id " + idFriend));
            friends.add(friend);
        }
        return friends;
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getMutualFriends(@PathVariable Long id, @PathVariable Long otherId) {
        User user = userService.findById(id).orElseThrow(() -> new NoSuchUserException("No such user with id " + id));
        User friend = userService.findById(otherId).orElseThrow(() -> new NoSuchUserException("No such user with id " + otherId));
        return userService.getMutualFriends(user, friend);
    }
}
