package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface FilmService {

    Film save(Film film);

    List<Film> findAll();

    Film update(Film film);

    Optional<Film> findById(long id);

    void addLike(Film film, User user);

    void deleteLike(Film film, User user);

    List<Film> getMostLikedFilms(Integer count);
}
