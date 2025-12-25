package edu.pjatk.tin.restaurant.domain.repository;

import edu.pjatk.tin.restaurant.domain.model.RestaurantUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantUserRepository extends JpaRepository<RestaurantUser, Long> {
}
