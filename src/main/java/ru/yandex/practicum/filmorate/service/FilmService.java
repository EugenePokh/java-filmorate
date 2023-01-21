package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmService {

    Film save(Film film);

    List<Film> findAll();

    Film update(Film film);
}
