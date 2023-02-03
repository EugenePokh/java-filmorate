package ru.yandex.practicum.filmorate.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FilmServiceImpl implements FilmService {

    private final FilmStorage filmStorage;

    @Override
    public Optional<Film> findById(long id) {
        return filmStorage.findById(id);
    }

    @Override
    public void addLike(Film film, User user) {
        film.getUserIdsForLikes().add(user.getId());
        filmStorage.save(film);
    }

    @Override
    public void deleteLike(Film film, User user) {
        film.getUserIdsForLikes().remove(user.getId());
        filmStorage.save(film);
    }

    @Override
    public List<Film> getMostLikedFilms(Integer count) {
        List<Film> films = filmStorage.findAll()
                .stream()
                .sorted(new MostLikedComparator())
                .limit(count)
                .collect(Collectors.toList());

        return films;
    }

    @Override
    public Film save(Film film) {
        return filmStorage.save(film);
    }

    @Override
    public Film update(Film film) {
        return filmStorage.update(film);
    }

    @Override
    public List<Film> findAll() {
        return filmStorage.findAll();
    }

}
