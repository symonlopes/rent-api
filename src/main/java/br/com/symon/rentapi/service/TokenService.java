package br.com.symon.rentapi.service;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Autowired
    private AuthenticationManager authenticationManager;

//    @Autowired
//    private JwtEncoder jwtEncoder;

//
//    public String generateToken(Authentication authentication) {
//
//        Instant now = Instant.now();
////        String scope = authentication.getAuthorities().stream()
////                .map(GrantedAuthority::getAuthority)
////                .collect(Collectors.joining(" "));
//
//        var scopes = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());
//
//        JwtClaimsSet claims = JwtClaimsSet.builder()
//                .issuer("self")
//                .claim("scope", scopes)
//                .issuedAt(now)
//                .expiresAt(now.plus(3, ChronoUnit.HOURS))
//                .subject(authentication.getName())
//                .build();
//
//        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
//    }

    public String generateToken(String email, String password) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email,password)
        );

        String username = authentication.getName();

        var key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        var scopes = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .claim("scope", scopes)
                .subject(username)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(3, ChronoUnit.HOURS)))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }
}