package ru.yandex.practicum.filmorate.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dto.FilmRequestDto;
import ru.yandex.practicum.filmorate.controller.dto.FilmResponseDto;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmGenreService;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class FilmMapper {

    private final MpaService mpaService;
    private final FilmGenreService filmGenreService;
    private final GenreService genreService;

    public Film toModel(FilmRequestDto filmRequestDto) {
        Film film = new Film();
        film.setName(filmRequestDto.getName());
        film.setDescription(filmRequestDto.getDescription());
        film.setDuration(filmRequestDto.getDuration());
        film.setReleaseDate(filmRequestDto.getReleaseDate());
        film.setRatingId(filmRequestDto.getMpa().getId());

        return film;
    }

    public FilmResponseDto toDto(Film film) {
        FilmResponseDto filmResponseDto = new FilmResponseDto();
        filmResponseDto.setId(film.getId());
        filmResponseDto.setName(film.getName());
        filmResponseDto.setDescription(film.getDescription());
        filmResponseDto.setReleaseDate(film.getReleaseDate());
        filmResponseDto.setDuration(film.getDuration());
        filmResponseDto.setUserIdsForLikes(film.getUserIdsForLikes());

        Mpa mpa = mpaService.findById(film.getRatingId()).get();
        filmResponseDto.setMpa(new FilmResponseDto.MpaDto(mpa.getId(), mpa.getName()));

        List<FilmResponseDto.GenreDto> genreDtos = filmGenreService.findAllByFilmId(film.getId())
                .stream()
                .map(filmGenre -> {
                    Genre genre = genreService.findById(filmGenre.getGenreId()).orElse(null);
                    return new FilmResponseDto.GenreDto(genre.getId(), genre.getName());
                })
                .collect(Collectors.toList());
        filmResponseDto.setGenres(genreDtos);

        return filmResponseDto;
    }
}
