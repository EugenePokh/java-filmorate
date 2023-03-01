package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controller.dto.UserRequestDto;
import ru.yandex.practicum.filmorate.controller.dto.UserResponseDto;
import ru.yandex.practicum.filmorate.controller.exception.NoSuchUserException;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class UserController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserMapper userMapper;
    private final UserService userService;

    @PostMapping("/users")
    public UserResponseDto createUser(@Validated @RequestBody UserRequestDto userRequestDto) {
        log.debug("Handle endpoint POST /users dto - " + userRequestDto);
        User user = userMapper.toModel(userRequestDto);
        User created = userService.save(user);

        return userMapper.toDto(created);
    }

    @PutMapping("/users")
    public UserResponseDto updateUser(@Validated @RequestBody UserRequestDto userRequestDto) {
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

        return userMapper.toDto(userService.update(user));
    }

    @GetMapping("/users")
    public List<UserResponseDto> findAll() {
        List<User> users = userService.findAll();
        List<UserResponseDto> dtos = new ArrayList<>();

        for (User user : users) {
            UserResponseDto dto = userMapper.toDto(user);
            dtos.add(dto);
        }

        return dtos;
    }

    @GetMapping("/users/{id}")
    public UserResponseDto getById(@PathVariable Long id) {
        return userMapper.toDto(userService.findById(id).orElseThrow(() -> new NoSuchUserException("No such user with id " + id)));
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
    public List<UserResponseDto> getFriends(@PathVariable Long id) {
        User user = userService.findById(id).orElseThrow(() -> new NoSuchUserException("No such user with id " + id));
        Set<Long> ids = userService.getFriendByUser(user)
                .stream()
                .map(Friend::getFriendId)
                .collect(Collectors.toSet());
        List<User> friends = new ArrayList<>();
        for (Long friend : ids) {
            User userFriend = userService.findById(friend).orElseThrow(() -> new NoSuchUserException("No such user with id " + friend));
            friends.add(userFriend);
        }
        List<UserResponseDto> dtos = new ArrayList<>();

        for (User friendUser : friends) {
            UserResponseDto dto = userMapper.toDto(friendUser);
            dtos.add(dto);
        }

        return dtos;
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<UserResponseDto> getMutualFriends(@PathVariable Long id, @PathVariable Long otherId) {
        User user = userService.findById(id).orElseThrow(() -> new NoSuchUserException("No such user with id " + id));
        User friend = userService.findById(otherId).orElseThrow(() -> new NoSuchUserException("No such user with id " + otherId));
        List<User> mutualFriends = userService.getMutualFriends(user, friend);

        List<UserResponseDto> dtos = new ArrayList<>();

        for (User mutualUser : mutualFriends) {
            UserResponseDto dto = userMapper.toDto(mutualUser);
            dtos.add(dto);
        }

        return dtos;
    }
}
