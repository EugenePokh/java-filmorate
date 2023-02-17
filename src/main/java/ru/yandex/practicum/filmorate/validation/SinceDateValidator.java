package ru.yandex.practicum.filmorate.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class SinceDateValidator implements ConstraintValidator<SinceDate, LocalDate> {

    private String sinceDate;

    @Override
    public void initialize(SinceDate constraintAnnotation) {
        sinceDate = constraintAnnotation.sinceDateCheck();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate s, ConstraintValidatorContext constraintValidatorContext) {
        String[] values = sinceDate.split("-");
        LocalDate since = LocalDate.of(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]));
        return s.isAfter(since);
    }
}
