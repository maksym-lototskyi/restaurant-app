package edu.pjatk.tin.restaurant.domain.repository;

import edu.pjatk.tin.restaurant.domain.model.TableType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TableTypeRepository extends JpaRepository<TableType, Long> {
    Optional<TableType> findByName(String name);
}
