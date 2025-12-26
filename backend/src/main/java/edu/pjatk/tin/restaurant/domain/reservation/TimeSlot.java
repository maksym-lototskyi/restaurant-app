package edu.pjatk.tin.restaurant.domain.reservation;

import edu.pjatk.tin.restaurant.util.validation.InvalidDateRangeException;
import edu.pjatk.tin.restaurant.util.validation.ValidationUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

@Embeddable
public record TimeSlot(
        @Column(name = "start_time", nullable = false)
        LocalDateTime startTime,
        @Column(name = "end_time", nullable = false)
        LocalDateTime endTime
) {
    public TimeSlot {
        ValidationUtils.requireNonNull(startTime, "Start time cannot be null");
        ValidationUtils.requireNonNull(endTime, "End time cannot be null");

        if (endTime.isBefore(startTime) || endTime.isEqual(startTime)) {
            throw new InvalidDateRangeException("End time must be after start time");
        }
    }
}
