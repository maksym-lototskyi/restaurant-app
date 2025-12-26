package edu.pjatk.tin.restaurant.domain.restaurant_user;

public interface PasswordHasher {
    String hash(String password);
    boolean matches(String password, String hashedPassword);
}
