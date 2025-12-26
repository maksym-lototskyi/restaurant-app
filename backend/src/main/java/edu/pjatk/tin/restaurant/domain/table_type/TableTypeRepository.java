package edu.pjatk.tin.restaurant.domain.table_type;

import edu.pjatk.tin.restaurant.domain.table_type.TableType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TableTypeRepository extends JpaRepository<TableType, Long> {
    Optional<TableType> findByName(String name);

    boolean existsByName(String name);
}
