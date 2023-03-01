package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.yandex.practicum.filmorate.controller.exception.NoSuchFilmException;
import ru.yandex.practicum.filmorate.controller.exception.NoSuchGenreException;
import ru.yandex.practicum.filmorate.controller.exception.NoSuchMpaException;
import ru.yandex.practicum.filmorate.controller.exception.NoSuchUserException;

@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler({NoSuchUserException.class, NoSuchFilmException.class, NoSuchGenreException.class, NoSuchMpaException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNotFoundException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("NOT FOUND", ex.getMessage()));
    }

    @Data
    @AllArgsConstructor
    public class ErrorResponse {
        String error;
        String description;

    }
}
