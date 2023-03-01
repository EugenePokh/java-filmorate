package ru.yandex.practicum.filmorate.controller.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.validation.SinceDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
public class FilmRequestDto {
    private Long id;

    @NotBlank()
    private String name;
    @Size(max = 200)
    private String description;
    @SinceDate()
    private LocalDate releaseDate;
    @Positive()
    @NotNull
    private Integer duration;

    private MpaDto mpa;

    private List<GenreDto> genres;

    @Data
    @NoArgsConstructor
    public static class GenreDto {
        private long id;

    }

    @Data
    @NoArgsConstructor
    public static class MpaDto {
        private long id;

    }

}
