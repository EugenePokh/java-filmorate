package ru.yandex.practicum.filmorate.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.LikeService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FilmServiceImpl implements FilmService {

    private final FilmStorage filmStorage;
    private final LikeService likeService;

    @Override
    public Optional<Film> findById(long id) {
        return filmStorage.findById(id);
    }

    @Override
    public void addLike(Film film, User user) {
        Like like = new Like();
        like.setUserId(user.getId());
        like.setFilmId(film.getId());
        likeService.save(like);
    }

    @Override
    public void deleteLike(Film film, User user) {
        likeService.deleteByUserIdAndFilmId(user.getId(), film.getId());
    }

    @Override
    public List<Film> getMostLikedFilms(Integer count) {
        List<Film> films = likeService.findMostPopularFilms(count)
                .stream()
                .map(filmId -> filmStorage.findById(filmId).get())
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
