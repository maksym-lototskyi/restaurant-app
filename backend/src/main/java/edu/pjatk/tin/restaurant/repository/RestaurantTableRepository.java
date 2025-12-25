package edu.pjatk.tin.restaurant.repository;

import edu.pjatk.tin.restaurant.domain.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {
}
