package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.controller.exception.NoSuchGenreException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@RestController
@AllArgsConstructor
public class GenreController {
    private final GenreService genreService;


    @GetMapping("/genres")
    public List<Genre> findAll() {
        return genreService.findAll();
    }


    @GetMapping("/genres/{id}")
    public Genre getById(@PathVariable Long id) {
        return genreService.findById(id).orElseThrow(() -> new NoSuchGenreException("No such genre with id " + id));
    }
}
