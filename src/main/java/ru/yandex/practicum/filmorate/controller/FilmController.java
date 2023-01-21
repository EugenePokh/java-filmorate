package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controller.dto.FilmRequestDto;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@AllArgsConstructor
public class FilmController {
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    private final FilmMapper filmMapper;
    private final FilmService filmService;

    @PostMapping("/films")
    public Film createFilm(@Validated @RequestBody FilmRequestDto filmRequestDto) {
        log.debug("Handle endpoint POST /films dto - " + filmRequestDto);
        Film film = filmMapper.toModel(filmRequestDto);
        Film created = filmService.save(film);
       return created;
    }

    @PutMapping("/films/{id}")
    public Film updateFilm(@PathVariable int id,
                           @Validated @RequestBody FilmRequestDto filmRequestDto) {
        log.debug("Handle endpoint PUT /films/id dto - " + filmRequestDto);
        Film film = filmMapper.toModel(filmRequestDto);
        film.setId(id);

        return filmService.update(film);
    }

    @GetMapping("/films")
    public List<Film> findAll() {
        return filmService.findAll();
    }
}
