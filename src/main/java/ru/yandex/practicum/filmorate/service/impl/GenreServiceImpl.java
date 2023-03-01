package ru.yandex.practicum.filmorate.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreStorage genreStorage;

    @Override
    public List<Genre> findAll() {
        return genreStorage.findAll();
    }

    @Override
    public Optional<Genre> findById(long id) {
        return genreStorage.findById(id);
    }
}
