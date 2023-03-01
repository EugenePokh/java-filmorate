package ru.yandex.practicum.filmorate.storage.like;

import ru.yandex.practicum.filmorate.model.Like;

import java.util.List;
import java.util.Optional;

public interface LikeStorage {

    Like save(Like like);

    void deleteByUserIdAndFilmId(Long userId, Long filmId);

    Optional<Like> findById(int id);

    List<Like> findAll();

    List<Integer> findMostPopularFilms(int limit);
}
