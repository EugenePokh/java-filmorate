package ru.yandex.practicum.filmorate.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dto.UserRequestDto;
import ru.yandex.practicum.filmorate.controller.dto.UserResponseDto;
import ru.yandex.practicum.filmorate.model.User;

@Component
public class UserMapper {
    public User toModel(UserRequestDto userRequestDto) {
        User user = new User();
        user.setEmail(userRequestDto.getEmail());
        user.setLogin(userRequestDto.getLogin());
        user.setName(userRequestDto.getName());

        user.setBirthday(userRequestDto.getBirthday());

        return user;
    }

    public UserResponseDto toDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setName(user.getName());
        userResponseDto.setBirthday(user.getBirthday());
        userResponseDto.setLogin(user.getLogin());
        userResponseDto.setEmail(user.getEmail());

        return userResponseDto;
    }
}
