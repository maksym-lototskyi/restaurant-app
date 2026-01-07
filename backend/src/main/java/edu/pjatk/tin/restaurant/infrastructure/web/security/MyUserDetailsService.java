package edu.pjatk.tin.restaurant.infrastructure.web.security;

import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUser;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUserId;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final RestaurantUserRepository userRepository;

    public MyUserDetailsService(RestaurantUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RestaurantUser user = userRepository.findByEmail_Value(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return mapToDetails(user);
    }

    public CustomUserPrincipal loadByUserId(UUID id){
        RestaurantUser user = userRepository.findById(RestaurantUserId.of(id))
                .orElseThrow(() -> new UsernameNotFoundException("User with id " + id + " not found"));

        return mapToDetails(user);
    }

    private CustomUserPrincipal mapToDetails(RestaurantUser user){
        UserDetails details = User.builder()
                .username(user.getEmail().value())
                .password(user.getPassword().hashedValue())
                .roles(user.getRole().name()).build();
        return new CustomUserPrincipal(user.getId().value(), details.getUsername(), details.getPassword(), details.getAuthorities());
    }

}
