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

@Service
@AllArgsConstructor
public class JWTservice {
    private UserService userService;
    private final String ENCRYPTION_KEY ="eFsvjkP9M2d1sc+miVGmkqFAhqt+cHhVZUWYe6P/SUs=";
    public Map<String, String> get(String username){
        User user = (User)this.userService.loadUserByUsername((username));
        return this.getJWT(user);
    }

    private Map<String, String> getJWT(User user) {
        final long cureentTime = System.currentTimeMillis();
        final long expireTime = cureentTime + 30*60*1000;

        Map<String, String> claims = Map.of(
                "name", user.getName(),
                "email", user.getEmail()
        );


        final String jeton = Jwts.builder().setIssuedAt(new Date(cureentTime))
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

}
