package edu.pjatk.tin.restaurant.application.table_type;

import edu.pjatk.tin.restaurant.domain.table_type.TableType;
import edu.pjatk.tin.restaurant.domain.table_type.TableTypeDimensions;

class TableTypeMapper {
    static TableTypeDetailsDto toDto(TableType tableType) {
        return new TableTypeDetailsDto(
                tableType.getId(),
                tableType.getName(),
                tableType.getNumberOfSeats(),
                tableType.getDimensions().length(),
                tableType.getDimensions().width()
        );
    }

    static TableType toEntity(CreateTableTypeDto createTableTypeDto) {
        return new TableType(
                createTableTypeDto.name(),
                createTableTypeDto.numberOfSeats(),
                TableTypeDimensions.of(
                        createTableTypeDto.width(),
                        createTableTypeDto.length()
                )
        );
    }
}
