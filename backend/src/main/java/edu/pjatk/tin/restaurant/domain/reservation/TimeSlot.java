package edu.pjatk.tin.restaurant.domain.reservation;

import edu.pjatk.tin.restaurant.util.ValidationUtil;
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
        ValidationUtil.requireNonNull(startTime, "Start time cannot be null");
        ValidationUtil.requireNonNull(endTime, "End time cannot be null");
        ValidationUtil.requireStartBeforeEnd(startTime, endTime, "Start time must be before end time");
    }

    public static TimeSlot of(LocalDateTime startTime, LocalDateTime endTime) {
        return new TimeSlot(startTime, endTime);
    }
}
