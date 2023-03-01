package ru.yandex.practicum.filmorate.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class FilmResponseDto {

    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private MpaDto mpa;
    private List<GenreDto> genres = new ArrayList<>();
    private Set<Long> userIdsForLikes = new HashSet<>();

    @Data
    @NoArgsConstructor
    public static class MpaDto {
        private long id;
        private String name;

        public MpaDto(long id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    @Data
    @NoArgsConstructor
    public static class GenreDto {
        private long id;
        private String name;

        public GenreDto(long id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
