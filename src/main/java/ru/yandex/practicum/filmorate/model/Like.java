package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Like {
    private Long id;
    private Long userId;
    private Long filmId;

    public Like(Long userId, Long filmId) {
        this.userId = userId;
        this.filmId = filmId;
    }
}
