package com.qa.auth.jwt.component;

import com.qa.auth.jwt.utils.SecurityConstants;
import com.qa.domain.users.models.UserPrincipal;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

import static com.qa.auth.jwt.utils.SecurityConstants.JWT_SECRET;

@Component
@Slf4j
@SuppressWarnings("javadocType")
public class JwtTokenProvider {

    private static String ERROR;
    private byte[] signingKey = JWT_SECRET.getBytes();

    public String generateToken(Authentication authentication) {

        var userPrincipal = (UserPrincipal) authentication.getPrincipal();

        var roles = userPrincipal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        var expiryDate = new Date(System.currentTimeMillis() + 108_000000);

        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setHeaderParam("type", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .setSubject(Long.toString(userPrincipal.getId()))
                .claim("username", userPrincipal.getUsername())
                .claim("roles", roles)
                .compact();
    }

    public Long getUserIdFromJWT(String token) {

        var claims = Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(signingKey)
                    .parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            ERROR = "Invalid JWT signature.";
            log.warn(ERROR);
        } catch (MalformedJwtException ex) {
            ERROR = "Invalid JWT token.";
            log.warn(ERROR);
        } catch (ExpiredJwtException ex) {
            ERROR = "Expired JWT token.";
            log.warn(ERROR);
        } catch (UnsupportedJwtException ex) {
            ERROR = "Unsupported JWT token.";
            log.warn(ERROR);
        } catch (IllegalArgumentException ex) {
            ERROR = "JWT claims string is empty.";
            log.warn(ERROR);
        }
        return false;
    }

    static String getERROR() {
        return ERROR;
    }
}