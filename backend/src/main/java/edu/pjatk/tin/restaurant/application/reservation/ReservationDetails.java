package edu.pjatk.tin.restaurant.application.reservation;

import edu.pjatk.tin.restaurant.application.restaurant_user.RestaurantUserProfileDetails;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReservationDetails(
        UUID id,
        int numberOfGuests,
        LocalDateTime reservationStart,
        LocalDateTime reservationEnd,
        String tableNumber,
        RestaurantUserProfileDetails customerDetails
) {
}
