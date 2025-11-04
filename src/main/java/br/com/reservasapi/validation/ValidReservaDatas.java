package br.com.reservasapi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReservaDatasValidator.class)
public @interface ValidReservaDatas {

    String message() default "A data de check-out deve ser posterior à data de check-in";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
