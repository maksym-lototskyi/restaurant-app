package edu.pjatk.tin.restaurant.domain.hall;

import edu.pjatk.tin.restaurant.domain.hall.Hall;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HallRepository extends JpaRepository<Hall, Long> {
    Optional<Hall> findByName(String name);
}
