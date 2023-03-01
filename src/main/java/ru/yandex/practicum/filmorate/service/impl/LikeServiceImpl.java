package ru.yandex.practicum.filmorate.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.service.LikeService;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LikeServiceImpl implements LikeService {


    private final LikeStorage likeStorage;

    @Override
    public Like save(Like like) {
        return likeStorage.save(like);
    }

    @Override
    public void deleteByUserIdAndFilmId(Long userId, Long filmId) {
        likeStorage.deleteByUserIdAndFilmId(userId, filmId);
    }

    @Override
    public Optional<Like> findById(int id) {
        return likeStorage.findById(id);
    }

    @Override
    public List<Like> findAll() {
        return likeStorage.findAll();
    }

    @Override
    public List<Integer> findMostPopularFilms(int limit) {
        return likeStorage.findMostPopularFilms(limit);
    }
}
