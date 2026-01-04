package edu.pjatk.tin.restaurant.infrastructure.adapter;

import edu.pjatk.tin.restaurant.domain.restaurant_user.PasswordHasher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
class ByCriptPasswordHasher implements PasswordHasher {
    private final PasswordEncoder encoder;

    ByCriptPasswordHasher(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public String hash(String password) {
        return encoder.encode(password);
    }

    @Override
    public boolean matches(String password, String hashedPassword) {
        return encoder.matches(password, hashedPassword);
    }
}
