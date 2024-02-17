package com.apogeeDocument.apogeeDocument.security;

import com.apogeeDocument.apogeeDocument.entites.Jwt;
import com.apogeeDocument.apogeeDocument.services.JwtService;
import com.apogeeDocument.apogeeDocument.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class JwtFilter extends OncePerRequestFilter {

    private UserService userService;
    private JwtService jwtService;

    public JwtFilter(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String userName = null;
        String token = null;
        Jwt tokenInDatabase = null;
        boolean isTokenExpired = true;

        final String authorisation = request.getHeader("Authorisation");
        if(authorisation != null && authorisation.startsWith("jeton ") ){
            token = authorisation.substring(6);
            tokenInDatabase = this.jwtService.tokenByValue(token);
            userName= jwtService.getUserName(token);
            isTokenExpired = jwtService.isTokenExpired(token);
        }

        if (!isTokenExpired
                && tokenInDatabase.getUser().getEmail().equals(userName)
                && userName != null
                && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userService.loadUserByUsername(userName);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails ,null,userDetails.getAuthorities() );

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}
