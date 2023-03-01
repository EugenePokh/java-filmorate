package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.FilmGenre;

import java.util.List;
import java.util.Optional;

public interface FilmGenreService {


    List<FilmGenre> findAllByFilmId(Long filmId);

    Optional<FilmGenre> findById(int id);

    void saveAllForFilm(List<FilmGenre> filmGenres);

    void deleteByFilmId(Long id);
}
