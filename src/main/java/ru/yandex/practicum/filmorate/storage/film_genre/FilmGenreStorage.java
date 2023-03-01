package ru.yandex.practicum.filmorate.storage.film_genre;

import ru.yandex.practicum.filmorate.model.FilmGenre;

import java.util.List;
import java.util.Optional;

public interface FilmGenreStorage {

    FilmGenre save(FilmGenre filmGenre);

    void deleteByFilmId(Long filmId);

    List<FilmGenre> findAllByFilmId(Long filmId);

    Optional<FilmGenre> findById(int id);
}
