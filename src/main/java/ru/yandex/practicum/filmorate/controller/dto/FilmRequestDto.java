package ru.yandex.practicum.filmorate.controller.dto;


import lombok.Data;
import ru.yandex.practicum.filmorate.validation.SinceDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class FilmRequestDto {

    @NotBlank()
    private String name;
    @Size(max = 200)
    private String description;
    @SinceDate()
    private LocalDate releaseDate;
    @Positive()
    @NotNull
    private Integer duration;
}
