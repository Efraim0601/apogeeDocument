package com.apogeeDocument.apogeeDocument.security;

import com.apogeeDocument.apogeeDocument.entites.User;
import com.apogeeDocument.apogeeDocument.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class JwtService {
    private UserService userService;
    private final String ENCRYPTION_KEY ="eFsvjkP9M2d1sc+miVGmkqFAhqt+cHhVZUWYe6P/SUs=";
    public Map<String, String> get(String username){
        User user = (User)this.userService.loadUserByUsername((username));
        return this.getJWT(user);
    }

    private Map<String, String> getJWT(User user) {
        final long cureentTime = System.currentTimeMillis();
        final long expireTime = cureentTime + 30*60*1000;

        final Map<String, Object> claims = Map.of(
                "name", user.getName(),
                Claims.EXPIRATION, new Date(expireTime),
                Claims.SUBJECT,user.getEmail()

        );


        final String jeton = Jwts.builder()
                .setIssuedAt(new Date(cureentTime))
                .setExpiration(new Date(expireTime))
                .setSubject(user.getEmail())
                .setClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        return Map.of("jeton", jeton);
    }

    private Key getKey() {
        final byte[] decode = Decoders.BASE64.decode(ENCRYPTION_KEY);
        return Keys.hmacShaKeyFor(decode);
    }

    public String getUserName(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = this.getClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date( ));
    }


    private <T> T getClaim(String token, Function<Claims, T> function) {
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
