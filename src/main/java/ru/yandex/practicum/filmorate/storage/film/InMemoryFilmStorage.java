package ru.yandex.practicum.filmorate.storage.film;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final Map<Long, Film> films = new HashMap<>();
    private long idCounter = 0;

    @Override
    public Optional<Film> findById(long id) {
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

    private long generateId() {
        return ++idCounter;
    }
}
