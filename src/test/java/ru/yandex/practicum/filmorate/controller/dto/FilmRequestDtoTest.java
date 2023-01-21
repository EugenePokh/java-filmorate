package ru.yandex.practicum.filmorate.controller.dto;

import org.junit.AfterClass;
import org.junit.BeforeClass;
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
import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class FilmRequestDtoTest {

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
        filmRequestDto.setDescription("12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
        filmRequestDto.setReleaseDate(new Date(1985, 12, 28));
        Set<ConstraintViolation<FilmRequestDto>> violations = validator.validate(filmRequestDto);

        assertTrue(violations.size() == 0);
    }

    @Test
    public void shouldReturnViolation() {
        FilmRequestDto filmRequestDto = new FilmRequestDto();
        filmRequestDto.setName("");
        filmRequestDto.setDuration(0);
        filmRequestDto.setDescription("012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
        filmRequestDto.setReleaseDate(new Date(1985, 12, 27));
        Set<ConstraintViolation<FilmRequestDto>> violations = validator.validate(filmRequestDto);

        assertTrue(violations.size() == 4);
    }

}