package ru.yandex.practicum.filmorate.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SinceDateValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SinceDate {
    String message() default "Invalid date";

    String sinceDateCheck() default "1985-12-27";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
