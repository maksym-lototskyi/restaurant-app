package edu.pjatk.tin.restaurant.domain.table_type;

import edu.pjatk.tin.restaurant.util.validation.ValidationUtils;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public record TableTypeId(UUID value) implements Serializable {
    public TableTypeId{
        ValidationUtils.requireNonNull(value, "TableTypeId value cannot be null");
    }
    public static TableTypeId generate() {
        return new TableTypeId(UUID.randomUUID());
    }

    public static TableTypeId of(UUID uuid) {
        return new TableTypeId(uuid);
    }
}
