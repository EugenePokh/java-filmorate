package ru.yandex.practicum.filmorate.controller.exception;



public class NoSuchFilmException extends RuntimeException {

    public NoSuchFilmException(String message) {
        super(message);
    }
}