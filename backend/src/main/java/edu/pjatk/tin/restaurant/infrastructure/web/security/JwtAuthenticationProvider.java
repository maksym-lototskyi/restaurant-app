package edu.pjatk.tin.restaurant.infrastructure.web.security;

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtUtil jwtUtil;
    private final MyUserDetailsService userDetailsService;
    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationProvider.class);

    public JwtAuthenticationProvider(JwtUtil jwtUtil, MyUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();

        if (token == null) {
            logger.warn("Token is null");
            throw new BadCredentialsException("Missing JWT token");
        }

        if(!jwtUtil.isTokenValid(token)){
            logger.warn("Invalid JWT token");
            throw new BadCredentialsException("Invalid JWT token");
        }
        UUID id = jwtUtil.getId(token);

        CustomUserPrincipal user = userDetailsService.loadByUserId(id);
        logger.info("Successfully authenticated user {}", user.getUsername());

        return new JwtAuthenticationToken(user, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(JwtAuthenticationToken.class);
    }
}
