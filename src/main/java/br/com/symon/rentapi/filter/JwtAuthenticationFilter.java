package br.com.symon.rentapi.filter;

import br.com.symon.rentapi.service.TokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

//@AllArgsConstructor
//@Component
public class JwtAuthenticationFilter /*extends OncePerRequestFilter*/ {
//
//    private final TokenService tokenService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//        final String authHeader = request.getHeader("Authorization");
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String token = authHeader.substring(7);
//        Claims claims = null;
//        try {
//            claims = tokenService.validateToken(token);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        String username = claims.getSubject();
//
//        var roles = claims.get("roles");
//
//        List<GrantedAuthority> authorities = new ArrayList<>();
//
//        if (roles instanceof List<?> roleList) {
//            for (Object role : roleList) {
//                if (role instanceof String roleName) {
//                    authorities.add(new SimpleGrantedAuthority(roleName));
//                }
//            }
//        }
//
//        Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
//        SecurityContextHolder.getContext().setAuthentication(auth);
//
//        filterChain.doFilter(request, response);
//    }
}