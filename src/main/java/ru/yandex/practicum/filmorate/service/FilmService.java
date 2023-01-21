package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmService {

    Film save(Film film);

    List<Film> findAll();

    Film update(Film film);

    Optional<Film> findById(int id);
}
