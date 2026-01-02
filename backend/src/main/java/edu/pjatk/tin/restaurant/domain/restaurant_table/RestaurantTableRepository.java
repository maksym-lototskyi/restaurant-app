package edu.pjatk.tin.restaurant.domain.restaurant_table;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, RestaurantTableId> {
    boolean existsByTableNumber(String tableNumber);
}
