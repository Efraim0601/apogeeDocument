package com.apogeeDocument.apogeeDocument.services;

import com.apogeeDocument.apogeeDocument.entites.Jwt;
import com.apogeeDocument.apogeeDocument.entites.User;
import com.apogeeDocument.apogeeDocument.repositories.JwtRepository;
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
import java.util.function.Function;

@Service
@AllArgsConstructor
public class JwtService {
    public static final String BEARER = "jeton";
    private UserService userService;
    private final JwtRepository jwtRepository;
    private final String ENCRYPTION_KEY ="eFsvjkP9M2d1sc+miVGmkqFAhqt+cHhVZUWYe6P/SUs=";



    public Jwt tokenByValue(String value){
        return this.jwtRepository.findByValueAndDisabledAndExpired(
                value,
                false,
                false
        ).orElseThrow(()-> new RuntimeException(""));
    }
    public Map<String, String> get(String username){
        User user = (User)this.userService.loadUserByUsername((username));
        final Map<String, String> jwtMap = this.getJWT(user);


        final Jwt  jwt = Jwt
                .builder()
                .value(BEARER)
                .disabled(false)
                .expired(false)
                .user(user)
                .build();
        this.jwtRepository.save(jwt);

        return  jwtMap;
    }

    private Map<String, String> getJWT(User user) {
        final long curentTime = System.currentTimeMillis();
        final long expireTime = curentTime + 30*60*1000;

        final Map<String, Object> claims = Map.of(
                "name", user.getName(),
                Claims.EXPIRATION, new Date(expireTime),
                Claims.SUBJECT,user.getEmail()

        );


        final String jeton = Jwts.builder()
                .setIssuedAt(new Date(curentTime))
                .setExpiration(new Date(expireTime))
                .setSubject(user.getEmail())
                .setClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        return Map.of(BEARER, jeton);
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
