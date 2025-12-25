package edu.pjatk.tin.restaurant.domain.repository;

import edu.pjatk.tin.restaurant.domain.model.RestaurantTable;
import edu.pjatk.tin.restaurant.domain.model.TableType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TableTypeRepository extends JpaRepository<TableType, Long> {
    Optional<TableType> findByName(String name);

    @Query("SELECT rt FROM RestaurantTable rt WHERE rt.hall.id = :hallId")
    List<RestaurantTable> findAllByHallId(Long hallId);
}
