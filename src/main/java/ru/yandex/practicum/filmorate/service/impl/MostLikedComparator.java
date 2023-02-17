package ru.yandex.practicum.filmorate.service.impl;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Comparator;

public class MostLikedComparator implements Comparator<Film> {

    @Override
    public int compare(Film o1, Film o2) {
        int film1Likes = o1.getUserIdsForLikes().size();
        int film2Likes = o2.getUserIdsForLikes().size();
        return Integer.compare(film2Likes, film1Likes);
    }
}
