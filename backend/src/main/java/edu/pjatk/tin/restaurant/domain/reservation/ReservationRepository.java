package edu.pjatk.tin.restaurant.domain.reservation;

import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTableId;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, ReservationId> {
    @Query("""
    SELECT COUNT(r) > 0
    FROM Reservation r
    WHERE r.timeSlot.startTime < :endTime
      AND r.timeSlot.endTime   > :startTime
      AND r.tableId = :tableId
      AND r.status = :status
      AND r.id <> :reservationId
""")
    boolean existsColliding(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("reservationId") ReservationId reservationId,
            @Param("tableId") RestaurantTableId tableId,
            @Param("status") ReservationStatus status
    );


    List<Reservation> findByCustomerId(RestaurantUserId customerId);
}
