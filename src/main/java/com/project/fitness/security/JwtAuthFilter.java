package com.project.fitness.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.function.ThrowingSupplier;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

//intercepts incoming req and validation
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Authentication Filter called");
        try{
            String jwt = parseJwt(request);
            System.out.println("jwt is "+jwt);

            if (jwt == null){
                throw new BadCredentialsException("Bad credentials");
            }

            if(jwt != null && jwtUtils.validateToken(jwt)){
                System.out.println("JWT validated and token = "+ jwt);
                String email = jwtUtils.getEmailFromToken(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                Claims claims = jwtUtils.getClaimsFromToken(jwt);
                List<String> roles = (List<String>) claims.get("role", List.class);
                System.out.println("roles = "+roles);
                List<GrantedAuthority> authorities = List.of();

                if (roles != null){
                    authorities = roles
                            .stream()
                            .map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role))
                            .toList();
                }

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Authentication Failed");
        }
        filterChain.doFilter(request, response);
        System.out.println("Authentication successful");
    }

    private String parseJwt(HttpServletRequest request) {
        String jwt = jwtUtils.getJwtFromHeader(request);
        return jwt;
    }


}