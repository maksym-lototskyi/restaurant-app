package edu.pjatk.tin.restaurant.domain.restaurant_table;

import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTable;
import edu.pjatk.tin.restaurant.domain.table_type.TableType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {
    @Query("SELECT rt FROM RestaurantTable rt WHERE rt.hall.id = :hallId")
    List<RestaurantTable> findAllByHallId(Long hallId);

    boolean existsByTableType(TableType tableType);
}
