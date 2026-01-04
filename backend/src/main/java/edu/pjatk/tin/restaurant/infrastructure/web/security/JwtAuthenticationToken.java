package edu.pjatk.tin.restaurant.infrastructure.web.security;

import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final String jwt;
    private final CustomUserPrincipal principal;

    public JwtAuthenticationToken(String jwt){
        super((Collection<? extends GrantedAuthority>) null);
        this.jwt = jwt;
        this.principal = null;
        setAuthenticated(false);
    }

    public JwtAuthenticationToken(CustomUserPrincipal principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.jwt = null;
        setAuthenticated(true);
    }

    @Override
    public @Nullable Object getCredentials() {
        return jwt;
    }

    @Override
    public @Nullable Object getPrincipal() {
        return principal;
    }
}
