package com.ghaith.mlika.springsecurity.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String secretKey = "2f2198207c02c0510b333a08f818e5c7cd2a738fe3bf59c2066093a9bea72cb2";

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    // generic method
    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey(){
        byte [] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // generate token
    public String generateToken(Map<String,Object> extraClaims,
                                UserDetails userDetails){
        // token will be valid 24 hours + 1 second
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    // validate token
    public boolean isTokenValid(String token, UserDetails userDetails){
        // make sure that username in token is same as the username in fct input
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // extract Expiration date from token

    private Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    // verify if token is not expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());

    }
}
