package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.controller.exception.NoSuchMpaException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@RestController
@AllArgsConstructor
public class MpaController {
    private final MpaService mpaService;


    @GetMapping("/mpa")
    public List<Mpa> findAll() {
        return mpaService.findAll();
    }


    @GetMapping("/mpa/{id}")
    public Mpa getById(@PathVariable Long id) {
        return mpaService.findById(id).orElseThrow(() -> new NoSuchMpaException("No such mpa with id " + id));
    }
}
