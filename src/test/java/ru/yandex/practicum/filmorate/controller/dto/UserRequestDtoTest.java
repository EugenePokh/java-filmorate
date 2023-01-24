package ru.yandex.practicum.filmorate.controller.dto;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserRequestDtoTest {
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
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setEmail("email@email.ru");
        userRequestDto.setLogin("aa");
        userRequestDto.setName("ab");
        userRequestDto.setBirthday(LocalDate.now().minusDays(1));

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);

        assertTrue(violations.size() == 0);
    }

    @Test
    public void shouldReturnViolationEmail() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setEmail("");
        userRequestDto.setLogin("aa");
        userRequestDto.setName("ab");
        userRequestDto.setBirthday(LocalDate.now().minusDays(1));
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);

        assertTrue(violations.size() == 1);
        assertEquals("email", violations.stream().findFirst().get().getPropertyPath().toString());
    }

    @Test
    public void shouldReturnViolationLogin() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setEmail("email@email.ru");
        userRequestDto.setLogin("a a");
        userRequestDto.setName("ab");
        userRequestDto.setBirthday(LocalDate.now().minusDays(1));
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);

        assertTrue(violations.size() == 1);
        assertEquals("login", violations.stream().findFirst().get().getPropertyPath().toString());
    }

    @Test
    public void shouldReturnViolationBirthday() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setEmail("email@email.ru");
        userRequestDto.setLogin("aa");
        userRequestDto.setName("ab");
        userRequestDto.setBirthday(LocalDate.of(2030, 01, 01));
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);

        assertTrue(violations.size() == 1);
        assertEquals("birthday", violations.stream().findFirst().get().getPropertyPath().toString());
    }

}