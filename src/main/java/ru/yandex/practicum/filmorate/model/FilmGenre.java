package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FilmGenre {
    private Long id;
    private Long genreId;
    private Long filmId;

    public FilmGenre(Long genreId, Long filmId) {
        this.genreId = genreId;
        this.filmId = filmId;
    }
}
