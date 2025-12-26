package edu.pjatk.tin.restaurant.domain.restaurant_table;

import edu.pjatk.tin.restaurant.domain.hall.HallDimensions;
import edu.pjatk.tin.restaurant.domain.table_type.TableTypeDimensions;
import edu.pjatk.tin.restaurant.util.validation.ValidationUtil;

public class TablePlacementValidator {
    public static void validatePosition(TablePosition position, HallDimensions hallDimensions, TableTypeDimensions tableDimensions) {

        switch (position.rotation()){
            case DEGREE_0, DEGREE_180 -> {
                ValidationUtil.requireValueInRange(position.positionX(), 0, hallDimensions.length() - tableDimensions.length(),
                        "Table position X must be within hall dimensions");
                ValidationUtil.requireValueInRange(position.positionY(), 0, hallDimensions.width() - tableDimensions.width(),
                        "Table position Y must be within hall dimensions");
            }
            case DEGREE_90, DEGREE_270 -> {
                ValidationUtil.requireValueInRange(position.positionX(), 0, hallDimensions.length() - tableDimensions.width(),
                        "Table position X must be within hall dimensions");
                ValidationUtil.requireValueInRange(position.positionY(), 0, hallDimensions.width() - tableDimensions.length(),
                        "Table position Y must be within hall dimensions");
            }
        }
    }
}
