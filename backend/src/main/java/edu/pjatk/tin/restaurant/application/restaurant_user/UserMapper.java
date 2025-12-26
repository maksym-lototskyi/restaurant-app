package edu.pjatk.tin.restaurant.application.restaurant_user;

import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUser;

public class UserMapper {
    public static UserDetails toDetails(RestaurantUser user) {
        return new UserDetails(
                user.getId().value(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }
}
