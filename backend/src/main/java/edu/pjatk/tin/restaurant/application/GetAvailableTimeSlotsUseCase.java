package edu.pjatk.tin.restaurant.application;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.reservation.ReservationRepository;
import edu.pjatk.tin.restaurant.domain.reservation.ReservationStatus;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTableRepository;
import edu.pjatk.tin.restaurant.util.validation.ValidationFailedException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@UseCase
public class GetAvailableTimeSlotsUseCase {
    private static final LocalTime CLOSING_TIME = LocalTime.of(22, 0, 0);
    private static final LocalTime OPENING_TIME = LocalTime.of(8, 0, 0);
    private static final int SLOT_DURATION = 2;
    private final ReservationRepository reservationRepository;
    private final RestaurantTableRepository tableRepository;

    public GetAvailableTimeSlotsUseCase(ReservationRepository reservationRepository, RestaurantTableRepository tableRepository) {
        this.reservationRepository = reservationRepository;
        this.tableRepository = tableRepository;
    }

    public List<LocalTime> execute(LocalDate date, LocalTime preferredStartTime, int numberOfGuests, int pageNumber, int pageSize){
        if(date.isBefore(LocalDate.now())) throw new ValidationFailedException("Start date must not be in the past");
        if(preferredStartTime.isBefore(OPENING_TIME) || preferredStartTime.isAfter(CLOSING_TIME.minusHours(SLOT_DURATION)))
            throw new ValidationFailedException("Preferred start time must be within restaurant operating hours");

        List<LocalTime> availableTimeSlots = new ArrayList<>();
        long allTables = tableRepository.countByNumberOfGuests(numberOfGuests);

        for(LocalTime time = findStartTime(date, preferredStartTime);
            !time.isAfter(CLOSING_TIME) && !time.plusHours(SLOT_DURATION).isAfter(CLOSING_TIME);
            time = time.plusMinutes(15)){

            LocalDateTime slotStart = LocalDateTime.of(date, time);
            LocalDateTime slotEnd = slotStart.plusHours(SLOT_DURATION);

            if(reservationRepository.countBusyTables(slotStart, slotEnd, ReservationStatus.CONFIRMED, numberOfGuests) < allTables)
                availableTimeSlots.add(time);
        }

        int totalSlots = availableTimeSlots.size();
        int fromIndex = Math.min(pageNumber * pageSize, totalSlots);
        int toIndex = Math.min(fromIndex + pageSize, totalSlots);

        return availableTimeSlots.subList(fromIndex, toIndex);
    }

    private static LocalTime findStartTime(LocalDate date, LocalTime preferredStartTime){
        if(date.equals(LocalDate.now())){
            LocalTime now = LocalTime.now();
            if(preferredStartTime.isBefore(now)){
                preferredStartTime = now;
            }
        }

        int minutes = preferredStartTime.getMinute() % 15 == 0 ? preferredStartTime.getMinute() : 15 * ((preferredStartTime.getMinute() / 15) + 1);

        LocalTime time = LocalTime.of(preferredStartTime.getHour(), 0);
        time = time.plusMinutes(minutes);

        return time.isBefore(OPENING_TIME) ? OPENING_TIME : time.isAfter(CLOSING_TIME.minusHours(SLOT_DURATION)) ? CLOSING_TIME : time;
    }
}
