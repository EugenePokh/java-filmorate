package ru.yandex.practicum.filmorate.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.service.FilmGenreService;
import ru.yandex.practicum.filmorate.storage.film_genre.FilmGenreStorage;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FilmGenreServiceImpl implements FilmGenreService {

    private final FilmGenreStorage filmGenreStorage;


    @Override
    public void saveAllForFilm(List<FilmGenre> filmGenres) {
        Long filmId = filmGenres.get(0).getFilmId();
        filmGenreStorage.deleteByFilmId(filmId);
        filmGenres.forEach(filmGenreStorage::save);
    }

    @Override
    public void deleteByFilmId(Long id) {
        filmGenreStorage.deleteByFilmId(id);
    }

    @Override
    public List<FilmGenre> findAllByFilmId(Long filmId) {
        return filmGenreStorage.findAllByFilmId(filmId);
    }

    @Override
    public Optional<FilmGenre> findById(int id) {
        return filmGenreStorage.findById(id);
    }
}
