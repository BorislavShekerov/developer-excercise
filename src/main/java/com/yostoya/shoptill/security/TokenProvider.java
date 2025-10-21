package com.yostoya.shoptill.security;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.yostoya.shoptill.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

import static com.auth0.jwt.JWT.create;
import static com.auth0.jwt.JWT.require;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.springframework.security.authentication.UsernamePasswordAuthenticationToken.authenticated;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenProvider {

    private final UserService userService;

    private static final String ISSUER = "Yo Stoya";

    private static final String CMS = "Software developer newbie";

    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 1_800_000; // 30m

    private static final String AUTHORITIES = "authorities";

    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 86_400_000; // 1 day

    @Value("${jwt.secret}")
    private String secret;

    public String createAccessToken(UserPrincipal userPrincipal) {
        return create()
                .withIssuer(ISSUER)
                .withAudience(CMS)
                .withIssuedAt(new Date())
                .withSubject(userPrincipal.getUsername())
                .withArrayClaim(AUTHORITIES, getClaimsFromUser(userPrincipal))
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .sign(HMAC512(secret));
    }

    public String createRefreshToken(UserPrincipal userPrincipal) {
        return create()
                .withIssuer(ISSUER)
                .withAudience(CMS)
                .withIssuedAt(new Date())
                .withSubject(userPrincipal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .sign(HMAC512(secret));
    }

    public List<GrantedAuthority> getAuthorities(String token) {
        String[] claims = getClaimsFromToken(token);
        return stream(claims)
                .map(SimpleGrantedAuthority::new)
                .collect(toList());
    }

    public Authentication getAuthentication(String email, List<GrantedAuthority> authorities, HttpServletRequest request) {
        final var authToken = authenticated(userService.getUserByEmail(email), null, authorities);
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authToken;
    }

    public boolean isTokenValid(String email, String token) {
        final var verifier = getJWTVerifier();
        return isNotEmpty(email) && isNotExpired(verifier, token);
    }

    public String getSubject(String token, HttpServletRequest request) {
        try {
            return getJWTVerifier()
                    .verify(token)
                    .getSubject();

        } catch (TokenExpiredException ex) {
            request.setAttribute("expiredMessage", ex.getMessage());
            throw ex;
        } catch (InvalidClaimException ex) {
            request.setAttribute("invalidClaim", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    private boolean isNotExpired(JWTVerifier verifier, String token) {
        return verifier.verify(token)
                .getExpiresAt()
                .after(new Date());
    }

    private String[] getClaimsFromUser(UserPrincipal userPrincipal) {
        return userPrincipal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);
    }

    private String[] getClaimsFromToken(String token) {
        return getJWTVerifier().verify(token)
                .getClaim(AUTHORITIES)
                .asArray(String.class);
    }

    private JWTVerifier getJWTVerifier() {
        try {
            return require(HMAC512(secret))
                    .withIssuer(ISSUER)
                    .build();

        } catch (JWTVerificationException ex) {
            log.error("Token validation fail.");
            throw new JWTVerificationException("Token cannot be verified.");
        }
    }

}
