package edu.pjatk.tin.restaurant.application.table_type;

import edu.pjatk.tin.restaurant.domain.table_type.TableType;

class TableTypeMapper {
    static TableTypeDetails toDetails(TableType tableType) {
        return new TableTypeDetails(
                tableType.getId().value(),
                tableType.getName(),
                tableType.getNumberOfSeats(),
                tableType.getDimensions().length(),
                tableType.getDimensions().width()
        );
    }
}
