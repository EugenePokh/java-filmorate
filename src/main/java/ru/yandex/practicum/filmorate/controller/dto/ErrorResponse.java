package ru.yandex.practicum.filmorate.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    String error;
    String description;

}
