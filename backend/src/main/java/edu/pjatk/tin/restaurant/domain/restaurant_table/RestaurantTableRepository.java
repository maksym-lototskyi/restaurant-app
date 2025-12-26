package edu.pjatk.tin.restaurant.domain.restaurant_table;

import edu.pjatk.tin.restaurant.domain.hall.HallId;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTable;
import edu.pjatk.tin.restaurant.domain.table_type.TableType;
import edu.pjatk.tin.restaurant.domain.table_type.TableTypeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, RestaurantTableId> {
    @Query("SELECT rt FROM RestaurantTable rt WHERE rt.hallId = :hallId")
    List<RestaurantTable> findAllByHallId(HallId hallId);

    boolean existsByTableTypeId(TableTypeId tableTypeId);
}
