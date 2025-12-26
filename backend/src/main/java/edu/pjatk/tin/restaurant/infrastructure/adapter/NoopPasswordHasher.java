package edu.pjatk.tin.restaurant.infrastructure.adapter;

import edu.pjatk.tin.restaurant.domain.restaurant_user.PasswordHasher;
import org.springframework.stereotype.Service;

@Service
class NoopPasswordHasher implements PasswordHasher {
    @Override
    public String hash(String password) {
        return password;
    }

    @Override
    public boolean matches(String password, String hashedPassword) {
        return password.equals(hashedPassword);
    }
}
