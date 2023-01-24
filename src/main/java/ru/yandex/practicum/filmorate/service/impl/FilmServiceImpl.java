package ru.yandex.practicum.filmorate.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.*;

@Service
public class FilmServiceImpl implements FilmService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final Map<Integer, Film> films = new HashMap<>();
    private int idCounter = 0;

    @Override
    public Optional<Film> findById(int id) {
        return Optional.ofNullable(films.get(id));
    }

    @Override
    public Film save(Film film) {
        film.setId(generateId());
        films.put(film.getId(), film);
        log.info("Film created - " + film);
        return film;
    }

    @Override
    public Film update(Film film) {
        log.info("Film updated - " + film);
        films.put(film.getId(), film);

        return films.get(film.getId());
    }

    @Override
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    private int generateId() {
        return ++idCounter;
    }
}
