package edu.pjatk.tin.restaurant.domain.restaurant_table;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, RestaurantTableId> {
    boolean existsByTableNumber(String tableNumber);
    @Query("""
        SELECT COUNT (rt) > 0 FROM RestaurantTable rt
        WHERE rt.tableNumber = :tableNumber
        AND rt.id <> :id
    """)
    boolean existsByTableNumberExceptSelf(String tableNumber, RestaurantTableId id);
    @Query("""
        SELECT COUNT(rt) FROM RestaurantTable rt
        WHERE rt.numberOfSeats >= :numberOfGuests
    """)
    long countByNumberOfGuests(@Param("numberOfGuests") int numberOfGuests);

    @Query("""
    SELECT t
    FROM RestaurantTable t
    WHERE t.numberOfSeats >= :numberOfGuests
      AND NOT EXISTS (
          SELECT r
          FROM Reservation r
          WHERE r.tableId = t.id
            AND r.timeSlot.startTime < :endTime
            AND r.timeSlot.endTime > :startTime
      )
    ORDER BY t.numberOfSeats ASC
""")
    List<RestaurantTable> findAvailableTable(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("numberOfGuests") int numberOfGuests,
            Pageable pageable
    );

}
