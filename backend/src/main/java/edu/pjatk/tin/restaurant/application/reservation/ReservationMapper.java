package edu.pjatk.tin.restaurant.application.reservation;

import edu.pjatk.tin.restaurant.domain.reservation.Reservation;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTable;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUser;
import edu.pjatk.tin.restaurant.application.restaurant_table.TableMapper;
import edu.pjatk.tin.restaurant.application.restaurant_user.UserMapper;

public class ReservationMapper {
    public static ReservationDetails toDetails(Reservation reservation, RestaurantTable table, RestaurantUser user) {
        return new ReservationDetails(
                reservation.getId().value(),
                reservation.getNumberOfGuests(),
                reservation.getTimeSlot().startTime(),
                reservation.getTimeSlot().endTime(),
                TableMapper.toDetails(table),
                UserMapper.toDetails(user)
        );
    }

    public static ReservationSummary toSummary(Reservation reservation) {
        return new ReservationSummary(
                reservation.getId().value(),
                reservation.getNumberOfGuests(),
                reservation.getTimeSlot().startTime(),
                reservation.getTimeSlot().endTime()
        );
    }
}
