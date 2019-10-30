package com.ryulth.jwtexample.security.jwt;

import com.ryulth.jwtexample.security.UnauthorizedException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;


@Slf4j
@Component
public class TokenProvider {

    private @Value("${jwt.secret.base64.access}")
    String accessSecretKey;
    private @Value("${jwt.secret.base64.refresh}")
    String refreshSecretKey;
    private @Value("${jwt.expiration-seconds.access}")
    int accessTokenExpirationSeconds;
    private @Value("${jwt.expiration-seconds.refresh}")
    int refreshTokenExpirationSeconds;

    private static final String AUTHORITIES_KEY = "userEmail";

    public String publishToken(String userEmail, boolean isAccessToken) {
        long now = (new Date()).getTime();
        Date validity = (isAccessToken) ? new Date(now + this.accessTokenExpirationSeconds*1000) :
                new Date(now + this.refreshTokenExpirationSeconds*1000);
        String key = (isAccessToken) ? this.accessSecretKey : this.refreshSecretKey;

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim(AUTHORITIES_KEY, userEmail)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, this.generateKey(key))
                .compact();

    }

    public String getUserEmail(String authToken, boolean isAccess) throws UnauthorizedException {
        String key = (isAccess) ? this.accessSecretKey : this.refreshSecretKey;
        String message = "";
        try {
            Jwts.parser().setSigningKey(this.generateKey(key)).parseClaimsJws(authToken);
            Claims claims = Jwts.parser().setSigningKey(this.generateKey(key))
                    .parseClaimsJws(authToken).getBody();
            return claims.get(AUTHORITIES_KEY).toString();
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature.");
            log.trace("Invalid JWT signature trace: {}", e);
            message = e.getMessage();
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            log.trace("Expired JWT token trace: {}", e);
            message = e.getMessage();
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {}", e);
            message = e.getMessage();
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: {}", e);
            message = e.getMessage();
        } catch (JwtException e) {
            log.info("JWT token are invalid.");
            log.trace("JWT token are invalid trace: {}", e);
            message = e.getMessage();
        }
        throw new UnauthorizedException(message);
    }

    private byte[] generateKey(String secretKey) {
        byte[] key = null;
        try {
            key = secretKey.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return key;
    }
}
