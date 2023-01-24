package ru.yandex.practicum.filmorate.controller.dto;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FilmRequestDtoTest {

    private static final int FILM_DESCRIPTION_MAX_LENGTH = 200;
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    public static void close() {
        validatorFactory.close();
    }

    @Test
    public void shouldNotReturnViolation() {
        FilmRequestDto filmRequestDto = new FilmRequestDto();
        filmRequestDto.setName("title");
        filmRequestDto.setDuration(1);
        filmRequestDto.setDescription("a".repeat(FILM_DESCRIPTION_MAX_LENGTH));
        filmRequestDto.setReleaseDate(LocalDate.of(1895, 12, 28));
        Set<ConstraintViolation<FilmRequestDto>> violations = validator.validate(filmRequestDto);

        assertTrue(violations.size() == 0);
    }

    @Test
    public void shouldReturnViolationName() {
        FilmRequestDto filmRequestDto = new FilmRequestDto();
        filmRequestDto.setName("");
        filmRequestDto.setDuration(1);
        filmRequestDto.setDescription("a".repeat(FILM_DESCRIPTION_MAX_LENGTH));
        filmRequestDto.setReleaseDate(LocalDate.of(1895, 12, 28));
        Set<ConstraintViolation<FilmRequestDto>> violations = validator.validate(filmRequestDto);

        assertTrue(violations.size() == 1);
        assertEquals("name", violations.stream().findFirst().get().getPropertyPath().toString());
    }

    @Test
    public void shouldReturnViolationDuration() {
        FilmRequestDto filmRequestDto = new FilmRequestDto();
        filmRequestDto.setName("title");
        filmRequestDto.setDuration(0);
        filmRequestDto.setDescription("a".repeat(FILM_DESCRIPTION_MAX_LENGTH));
        filmRequestDto.setReleaseDate(LocalDate.of(1895, 12, 28));
        Set<ConstraintViolation<FilmRequestDto>> violations = validator.validate(filmRequestDto);

        assertTrue(violations.size() == 1);
        assertEquals("duration", violations.stream().findFirst().get().getPropertyPath().toString());

    }

    @Test
    public void shouldReturnViolationDescription() {
        FilmRequestDto filmRequestDto = new FilmRequestDto();
        filmRequestDto.setName("title");
        filmRequestDto.setDuration(1);
        filmRequestDto.setDescription("a".repeat(FILM_DESCRIPTION_MAX_LENGTH)+"a");
        filmRequestDto.setReleaseDate(LocalDate.of(1895, 12, 28));
        Set<ConstraintViolation<FilmRequestDto>> violations = validator.validate(filmRequestDto);

        assertTrue(violations.size() == 1);
        assertEquals("description", violations.stream().findFirst().get().getPropertyPath().toString());

    }

    @Test
    public void shouldReturnViolationReleaseDate() {
        FilmRequestDto filmRequestDto = new FilmRequestDto();
        filmRequestDto.setName("title");
        filmRequestDto.setDuration(1);
        filmRequestDto.setDescription("a".repeat(FILM_DESCRIPTION_MAX_LENGTH));
        filmRequestDto.setReleaseDate(LocalDate.of(1895, 12, 27));
        Set<ConstraintViolation<FilmRequestDto>> violations = validator.validate(filmRequestDto);

        assertTrue(violations.size() == 1);
        assertEquals("releaseDate", violations.stream().findFirst().get().getPropertyPath().toString());

    }
}