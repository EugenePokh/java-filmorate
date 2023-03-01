package ru.yandex.practicum.filmorate.controller.exception;

public class NoSuchGenreException extends RuntimeException {

    public NoSuchGenreException(String message) {
        super(message);
    }
}
