package edu.pjatk.tin.restaurant.application.reservation;

import edu.pjatk.tin.restaurant.application.restaurant_table.TableDetails;
import edu.pjatk.tin.restaurant.application.restaurant_user.UserDetails;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReservationDetails(
        UUID id,
        int numberOfGuests,
        LocalDateTime reservationStart,
        LocalDateTime reservationEnd,
        TableDetails tableDetails,
        UserDetails customerDetails
) {
}
