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

    public List<LocalTime> execute(LocalDateTime startTime, int numberOfGuests, int pageNumber, int pageSize){
        if(startTime.isBefore(LocalDateTime.now())) throw new ValidationFailedException("Start time must not be in the past");
        if(!startTime.toLocalTime().isBefore(CLOSING_TIME)) throw new ValidationFailedException("Start time must be before closing time");
        if(startTime.toLocalTime().isBefore(OPENING_TIME)) throw new ValidationFailedException("Start time must be after opening time");

        List<LocalTime> availableTimeSlots = new ArrayList<>();
        LocalDate date = startTime.toLocalDate();
        long allTables = tableRepository.countByNumberOfGuests(numberOfGuests);

        for(LocalTime time = startTime.toLocalTime();
            !time.plusHours(SLOT_DURATION).isAfter(CLOSING_TIME);
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
}
