package ru.yandex.practicum.filmorate.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class SinceDateValidator implements ConstraintValidator<SinceDate, Date> {

    private String sinceDate;

    @Override
    public void initialize(SinceDate constraintAnnotation) {
        sinceDate = constraintAnnotation.sinceDateCheck();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Date s, ConstraintValidatorContext constraintValidatorContext) {
        String[] values = sinceDate.split("-");
        Date since = new Date(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]));
        return s.after(since);
    }
}
