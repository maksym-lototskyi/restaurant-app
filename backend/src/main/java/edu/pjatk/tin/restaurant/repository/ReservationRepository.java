package edu.pjatk.tin.restaurant.repository;

import edu.pjatk.tin.restaurant.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
