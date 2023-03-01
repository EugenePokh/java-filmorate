package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controller.dto.FilmRequestDto;
import ru.yandex.practicum.filmorate.controller.dto.FilmResponseDto;
import ru.yandex.practicum.filmorate.controller.exception.NoSuchFilmException;
import ru.yandex.practicum.filmorate.controller.exception.NoSuchUserException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmGenreService;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class FilmController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final FilmMapper filmMapper;
    private final FilmService filmService;
    private final UserService userService;
    private final FilmGenreService filmGenreService;

    @PostMapping("/films")
    public FilmResponseDto createFilm(@Validated @RequestBody FilmRequestDto filmRequestDto) {
        log.debug("Handle endpoint POST /films dto - " + filmRequestDto);
        Film film = filmMapper.toModel(filmRequestDto);
        Film created = filmService.save(film);

        if (filmRequestDto.getGenres() != null) {
            List<FilmGenre> filmGenres = filmRequestDto.getGenres()
                    .stream()
                    .distinct()
                    .map(genreDto -> new FilmGenre(genreDto.getId(), created.getId()))
                    .collect(Collectors.toList());

            filmGenreService.saveAllForFilm(filmGenres);
        }

        return filmMapper.toDto(created);
    }

    @PutMapping("/films")
    public FilmResponseDto updateFilm(@Validated @RequestBody FilmRequestDto filmRequestDto) {
        long id;
        if (Objects.nonNull(filmRequestDto.getId())) {
            id = filmRequestDto.getId();
            filmService.findById(id).orElseThrow(() -> new NoSuchFilmException("No such film with id " + id));
        } else {
            throw new ValidationException("Has no id in dto");
        }
        log.debug("Handle endpoint PUT /films/id dto - " + filmRequestDto);
        Film film = filmMapper.toModel(filmRequestDto);
        film.setId(id);

        Film updated = filmService.update(film);

        if (filmRequestDto.getGenres() != null && filmRequestDto.getGenres().size() > 0) {
            List<FilmGenre> filmGenres = filmRequestDto.getGenres()
                    .stream()
                    .distinct()
                    .map(genreDto -> new FilmGenre(genreDto.getId(), updated.getId()))
                    .collect(Collectors.toList());

            filmGenreService.saveAllForFilm(filmGenres);
        } else {
            filmGenreService.deleteByFilmId(updated.getId());
        }

        return filmMapper.toDto(updated);
    }

    @GetMapping("/films")
    public List<FilmResponseDto> findAll() {
        return filmService.findAll()
                .stream()
                .map(filmMapper::toDto)
                .collect(Collectors.toList());
    }


    @GetMapping("/films/{id}")
    public FilmResponseDto getById(@PathVariable Long id) {
        return filmMapper.toDto(filmService.findById(id).orElseThrow(() -> new NoSuchFilmException("No such film with id " + id)));
    }

    @PutMapping("films/{id}/like/{userId}")
    public void addLike(@PathVariable Long id, @PathVariable Long userId) {
        Film film = filmService.findById(id).orElseThrow(() -> new NoSuchFilmException("No such film with id " + id));
        User user = userService.findById(userId).orElseThrow(() -> new NoSuchUserException("No such user with id " + userId));
        filmService.addLike(film, user);
    }

    @DeleteMapping("films/{id}/like/{userId}")
    public void deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        Film film = filmService.findById(id).orElseThrow(() -> new NoSuchFilmException("No such film with id " + id));
        User user = userService.findById(userId).orElseThrow(() -> new NoSuchUserException("No such user with id " + userId));
        filmService.deleteLike(film, user);
    }

    @GetMapping("/films/popular")
    public List<FilmResponseDto> getPopularFilms(@RequestParam(name = "count", required = false, defaultValue = "10") Integer count) {
        return filmService.getMostLikedFilms(count)
                .stream()
                .map(filmMapper::toDto)
                .collect(Collectors.toList());
    }
}
