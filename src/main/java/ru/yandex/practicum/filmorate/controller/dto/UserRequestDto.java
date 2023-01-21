package ru.yandex.practicum.filmorate.controller.dto;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
public class UserRequestDto {
    private Integer id;

    @NotBlank
    @Email()
    private String email;
    @NotBlank
    @Pattern(regexp = "\\w+\\.?")
    private String login;

    private String name;

    @Past()
    private LocalDate birthday;


}
