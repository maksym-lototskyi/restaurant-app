package edu.pjatk.tin.restaurant.infrastructure.web.controller;

import edu.pjatk.tin.restaurant.application.restaurant_user.RegisterUserUseCase;
import edu.pjatk.tin.restaurant.application.restaurant_user.RestaurantUserProfileDetails;
import edu.pjatk.tin.restaurant.domain.restaurant_user.Email;
import edu.pjatk.tin.restaurant.domain.restaurant_user.Password;
import edu.pjatk.tin.restaurant.domain.restaurant_user.PasswordHasher;
import edu.pjatk.tin.restaurant.infrastructure.web.constraint_validation.ValidPassword;
import edu.pjatk.tin.restaurant.infrastructure.web.dto.CreateUserDto;
import edu.pjatk.tin.restaurant.infrastructure.web.dto.UserInfoDto;
import edu.pjatk.tin.restaurant.infrastructure.web.security.CustomUserPrincipal;
import edu.pjatk.tin.restaurant.infrastructure.web.security.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@Validated
public class AuthController {
    private final RegisterUserUseCase registerUserUseCase;
    private final PasswordHasher passwordHasher;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(RegisterUserUseCase registerUserUseCase, PasswordHasher passwordHasher, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.registerUserUseCase = registerUserUseCase;
        this.passwordHasher = passwordHasher;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @RequestParam @jakarta.validation.constraints.Email String email,
            @RequestParam @ValidPassword String password,
            HttpServletResponse response
    ) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        String token = jwtUtil.generateToken((CustomUserPrincipal) Objects.requireNonNull(auth.getPrincipal()));

        Cookie jwtCookie = new Cookie("JWT_TOKEN", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(7 * 24 * 60 * 60);

        response.addCookie(jwtCookie);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<RestaurantUserProfileDetails> createUser(@Valid @RequestBody CreateUserDto dto) {
        RestaurantUserProfileDetails userDetails = registerUserUseCase.execute(
                dto.firstName(),
                dto.lastName(),
                Email.of(dto.email()),
                Password.fromRaw(dto.password(), passwordHasher)
        );
        return ResponseEntity.ok(userDetails);
    }

    @GetMapping("/me")
    public ResponseEntity<UserInfoDto> me(Authentication authentication) {
        if(authentication == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        CustomUserPrincipal details = (CustomUserPrincipal) authentication.getPrincipal();
        var info = new UserInfoDto(details.getId(), details.getUsername(), details.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        return ResponseEntity.ok(info);
    }
}

