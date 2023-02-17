package ru.yandex.practicum.filmorate.controller.dto;


import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class UserRequestDto {
    private Long id;

    @NotBlank
    @Email()
    private String email;
    @NotBlank
    @Pattern(regexp = "\\w+\\.?")
    private String login;

    private String name;

    @PastOrPresent()
    private LocalDate birthday;


}
