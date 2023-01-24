package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controller.dto.UserRequestDto;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Objects;

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
        int id;
        if (Objects.nonNull(userRequestDto.getId())) {
            id = userRequestDto.getId();
            userService.findById(id).orElseThrow(() -> new ValidationException("Has no id in dto"));
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
}
