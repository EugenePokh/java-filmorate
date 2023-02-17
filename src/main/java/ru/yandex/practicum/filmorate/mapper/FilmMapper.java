package ru.yandex.practicum.filmorate.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dto.FilmRequestDto;
import ru.yandex.practicum.filmorate.model.Film;

@Component
public class FilmMapper {

    public Film toModel(FilmRequestDto filmRequestDto) {
        Film film = new Film();
        film.setName(filmRequestDto.getName());
        film.setDescription(filmRequestDto.getDescription());
        film.setDuration(filmRequestDto.getDuration());
        film.setReleaseDate(filmRequestDto.getReleaseDate());

        return film;
    }
}
