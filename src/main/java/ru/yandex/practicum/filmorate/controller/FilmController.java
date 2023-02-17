package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controller.dto.FilmRequestDto;
import ru.yandex.practicum.filmorate.exception.NoSuchFilmException;
import ru.yandex.practicum.filmorate.exception.NoSuchUserException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Objects;

@RestController
@AllArgsConstructor
public class FilmController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final FilmMapper filmMapper;
    private final FilmService filmService;
    private final UserService userService;

    @PostMapping("/films")
    public Film createFilm(@Validated @RequestBody FilmRequestDto filmRequestDto) {
        log.debug("Handle endpoint POST /films dto - " + filmRequestDto);
        Film film = filmMapper.toModel(filmRequestDto);
        Film created = filmService.save(film);
        return created;
    }

    @PutMapping("/films")
    public Film updateFilm(@Validated @RequestBody FilmRequestDto filmRequestDto) {
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

        return filmService.update(film);
    }

    @GetMapping("/films")
    public List<Film> findAll() {
        return filmService.findAll();
    }


    @GetMapping("/films/{id}")
    public Film getById(@PathVariable Long id) {
        return filmService.findById(id).orElseThrow(() -> new NoSuchFilmException("No such film with id " + id));
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
    public List<Film> getPopularFilms(@RequestParam(name = "count", required = false, defaultValue = "10") Integer count) {
        return filmService.getMostLikedFilms(count);
    }
}
