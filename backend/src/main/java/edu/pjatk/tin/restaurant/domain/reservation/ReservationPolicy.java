package edu.pjatk.tin.restaurant.domain.reservation;

import edu.pjatk.tin.restaurant.util.validation.ValidationFailedException;

import java.time.LocalTime;

public class ReservationPolicy {
    public static void validate(TimeSlot slot) {
        LocalTime start = slot.startTime().toLocalTime();
        LocalTime end   = slot.endTime().toLocalTime();

        if (start.isBefore(LocalTime.of(8, 0)) ||
                end.isAfter(LocalTime.of(22, 0))) {
            throw new ValidationFailedException("Reservations allowed only between 08:00 and 22:00");
        }

        if (start.getMinute() % 15 != 0 || end.getMinute() % 15 != 0) {
            throw new ValidationFailedException("Reservations must be in 15-minute increments");
        }
    }
}
