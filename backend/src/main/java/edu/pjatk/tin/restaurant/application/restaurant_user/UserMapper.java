package edu.pjatk.tin.restaurant.application.restaurant_user;

import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUser;

public class UserMapper {
    public static RestaurantUserDetails toDetails(RestaurantUser user) {
        return new RestaurantUserDetails(
                user.getId().value(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail().value()
        );
    }
}
