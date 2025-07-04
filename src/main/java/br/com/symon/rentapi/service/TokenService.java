package br.com.symon.rentapi.service;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date now = new Date();
        Date expiryDate =  new Date(now.getTime() + jwtExpiration);

        var key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        var scopes = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .claim("scope", scopes)
                .subject(username)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }
//
//    public String generateToken(Authentication authentication) {
//        String username = authentication.getName();
//        Date now = new Date();
//        Date expiryDate =  new Date(now.getTime() + jwtExpiration);
//
//        var key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
//
//        var scopes = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());
//
//        return Jwts.builder()
//                .claim("scope", scopes)
//                .subject(username)
//                .issuedAt(now)
//                .expiration(expiryDate)
//                .signWith(key, Jwts.SIG.HS512)
//                .compact();
//    }
//
//    public String generateToken(Authentication authentication) throws Exception {
//        String username = authentication.getName();
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + jwtExpiration);
//
//        PrivateKey privateKey = loadPrivateKey();
//
//        var scope = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());
//
//        return Jwts.builder()
//                .claim("scope", scope)
//                .subject(username)
//                .issuedAt(now)
//                .expiration(expiryDate)
//                .signWith(privateKey, Jwts.SIG.RS256)
//                .compact();
//    }
//
//    public PrivateKey loadPrivateKey() throws Exception {
//        String key = Files.readString(Paths.get("src/main/resources/certs/private.pem"));
//        key = key.replaceAll("-----\\w+ PRIVATE KEY-----", "").replaceAll("\\s", "");
//        byte[] keyBytes = Base64.getDecoder().decode(key);
//
//        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
//        KeyFactory kf = KeyFactory.getInstance("RSA");
//        return kf.generatePrivate(spec);
//    }
//
//    public PublicKey loadPublicKey() throws Exception {
//        String key = Files.readString(Paths.get("src/main/resources/certs/public.pem"));
//        key = key.replaceAll("-----\\w+ PUBLIC KEY-----", "").replaceAll("\\s", "");
//        byte[] keyBytes = Base64.getDecoder().decode(key);
//
//        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
//        KeyFactory kf = KeyFactory.getInstance("RSA");
//        return kf.generatePublic(spec);
//    }

//    public Claims validateToken(String token) throws Exception {
//        PublicKey publicKey = loadPublicKey();
//        var payload = Jwts.parser()
//                .verifyWith(publicKey)
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//
//        return payload;
//    }
}