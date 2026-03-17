package com.company.empresa.util;

import com.company.empresa.entity.Permission;
import com.company.empresa.entity.Role;
import com.company.empresa.entity.User;
import com.company.empresa.model.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JwtUtil {

    @Value("${jwt.secret}")
    private String JWT_TOKEN;

    @Value("${jwt.expiration}")
    private Integer JWT_EXPIRATION;

    public String generateToken(UserPrincipal user){
        Map<String, Object> claims = new HashMap<>();

        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        claims.put("roles", roles);

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date (System.currentTimeMillis() + JWT_EXPIRATION))
                .and()
                .signWith(getKey())
                .compact();
    }



    public String extractName(String token){
        return extractClaims(token, Claims::getSubject);
    }

    public <T> T extractClaims(String token, Function<Claims, T> ClaimResolver){
        final Claims claims = extractAllClaims(token);
        return ClaimResolver.apply(claims);
    }

    public Date extractExpiration(String token){
        return extractClaims(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public boolean isValidToken(String token, UserDetails userDetails){
        final String userName = userDetails.getUsername();
        return (userName.equals(extractName(token)) && !isTokenExpired(token));
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public SecretKey getKey(){
        byte [] token = Decoders.BASE64.decode(JWT_TOKEN);
        return Keys.hmacShaKeyFor(token);
    }
}
