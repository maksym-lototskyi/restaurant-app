package edu.pjatk.tin.restaurant.domain.restaurant_table;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, RestaurantTableId> {
    boolean existsByTableNumber(String tableNumber);
    @Query("""
        SELECT COUNT(rt) FROM RestaurantTable rt
        WHERE rt.numberOfSeats >= :numberOfGuests
    """)
    long countByNumberOfGuests(@Param("numberOfGuests") int numberOfGuests);
}
