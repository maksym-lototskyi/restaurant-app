package edu.pjatk.tin.restaurant.domain.restaurant_user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RestaurantUserRepository extends JpaRepository<RestaurantUser, RestaurantUserId> {

    boolean existsByEmail(Email email);
    @Query("""
        SELECT COUNT (ru) > 0 FROM RestaurantUser ru
        WHERE ru.email = :email
        AND ru.id <> :userId
    """)
    boolean existsByEmailExceptSelf(RestaurantUserId userId, Email email);
}
