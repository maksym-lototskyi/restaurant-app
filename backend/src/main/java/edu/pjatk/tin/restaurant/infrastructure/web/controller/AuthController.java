package edu.pjatk.tin.restaurant.infrastructure.web.controller;

import edu.pjatk.tin.restaurant.infrastructure.web.dto.UserInfoDto;
import edu.pjatk.tin.restaurant.infrastructure.web.security.CustomUserPrincipal;
import edu.pjatk.tin.restaurant.infrastructure.web.security.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletResponse response
    ) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        String token = jwtUtil.generateToken((UserDetails) auth.getPrincipal());

        Cookie jwtCookie = new Cookie("JWT_TOKEN", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(7 * 24 * 60 * 60);

        response.addCookie(jwtCookie);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserInfoDto> me(Authentication authentication) {
        if(authentication == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        CustomUserPrincipal details = (CustomUserPrincipal) authentication.getPrincipal();
        var info = new UserInfoDto(details.getId(), details.getUsername(), details.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        return ResponseEntity.ok(info);
    }
}

