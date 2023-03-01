package ru.yandex.practicum.filmorate.controller.exception;


public class NoSuchUserException extends RuntimeException {

    public NoSuchUserException(String message) {
        super(message);
    }
}
