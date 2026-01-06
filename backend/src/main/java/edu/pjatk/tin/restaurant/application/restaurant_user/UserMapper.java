package edu.pjatk.tin.restaurant.application.restaurant_user;

import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUser;

public class UserMapper {
    public static RestaurantUserProfileDetails toDetails(RestaurantUser user) {
        return new RestaurantUserProfileDetails(
                user.getId().value(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail().value()
        );
    }

    public static RestaurantUserAdminDetails toAdminDetails(RestaurantUser user){
        return new RestaurantUserAdminDetails(
                user.getId().value(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail().value(),
                user.getRole().name()
        );
    }
}
