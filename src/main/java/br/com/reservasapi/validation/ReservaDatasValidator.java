package br.com.reservasapi.validation;

import br.com.reservasapi.dto.ReservaDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ReservaDatasValidator implements ConstraintValidator<ValidReservaDatas, ReservaDto> {
    @Override
    public boolean isValid(ReservaDto dto, ConstraintValidatorContext context) {
        if (dto.getDataCheckIn() == null || dto.getDataCheckOut() == null) {
            return true;
        }

        boolean valido = dto.getDataCheckOut().isAfter(dto.getDataCheckIn());

        if (!valido) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("A data de check-out deve ser posterior à data de check-in")
                    .addPropertyNode("dataCheckOut")
                    .addConstraintViolation();
        }

        return valido;
    }

}
