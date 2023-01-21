package ru.yandex.practicum.filmorate.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dto.UserRequestDto;
import ru.yandex.practicum.filmorate.model.User;

@Component
public class UserMapper {
    public User toModel(UserRequestDto userRequestDto) {
        User user = new User();
        user.setEmail(userRequestDto.getEmail());
        user.setLogin(userRequestDto.getLogin());

        if (userRequestDto.getName() == null || userRequestDto.getName().isBlank()) {
            user.setName(userRequestDto.getLogin());
        } else {
            user.setName(userRequestDto.getName());
        }

        user.setBirthday(userRequestDto.getBirthday());

        return user;
    }
}
