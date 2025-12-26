package edu.pjatk.tin.restaurant.domain.restaurant_table;

import edu.pjatk.tin.restaurant.domain.hall.HallDimensions;
import edu.pjatk.tin.restaurant.domain.table_type.TableTypeDimensions;
import edu.pjatk.tin.restaurant.util.validation.ValidationUtils;

public class TablePlacementValidator {
    public static void validatePosition(TablePosition position, TableTypeDimensions dimensions, HallDimensions hallDimensions) {
        switch (position.rotation()){
            case DEGREE_0, DEGREE_180 -> {
                ValidationUtils.requireValueInRange(position.positionX(), 0, hallDimensions.length() - dimensions.length(),
                        "Table position X must be within hall dimensions");
                ValidationUtils.requireValueInRange(position.positionY(), 0, hallDimensions.width() - dimensions.width(),
                        "Table position Y must be within hall dimensions");
            }
            case DEGREE_90, DEGREE_270 -> {
                ValidationUtils.requireValueInRange(position.positionX(), 0, hallDimensions.length() - dimensions.width(),
                        "Table position X must be within hall dimensions");
                ValidationUtils.requireValueInRange(position.positionY(), 0, hallDimensions.width() - dimensions.length(),
                        "Table position Y must be within hall dimensions");
            }
        }
    }
}
