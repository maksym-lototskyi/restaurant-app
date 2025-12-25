package edu.pjatk.tin.restaurant.repository;

import edu.pjatk.tin.restaurant.domain.RestaurantUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantUserRepository extends JpaRepository<RestaurantUser, Long> {
}
