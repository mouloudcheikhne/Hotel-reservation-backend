package hotel.example.hotelreservaion.validation;

import hotel.example.hotelreservaion.model.BookingStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidBookingStatusValidator implements ConstraintValidator<ValidBookingStatus, BookingStatus> {

    @Override
    public void initialize(ValidBookingStatus constraintAnnotation) {
    }

    @Override
    public boolean isValid(BookingStatus value, ConstraintValidatorContext context) {
        if (value == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Status is required. Valid statuses are: PENDING, CONFIRMED, CANCELLED")
                   .addConstraintViolation();
            return false;
        }
        
        // Vérifier que le statut est un des statuts valides
        for (BookingStatus status : BookingStatus.values()) {
            if (status.equals(value)) {
                return true;
            }
        }
        
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("Invalid status. Valid statuses are: PENDING, CONFIRMED, CANCELLED")
               .addConstraintViolation();
        return false;
    }
}
