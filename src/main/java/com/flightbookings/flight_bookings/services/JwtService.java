package com.flightbookings.flight_bookings.services;

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

/**
 * Service for generating and validating JSON Web Tokens (JWT).
 */
@Service
public class JwtService {

    private static final String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B59703373367639792";

    /**
     * Generates a JWT token for a given user.
     *
     * @param user the user for whom the token is generated.
     * @return the generated JWT token.
     */
    public String getTokenService(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }

    /**
     * Generates a JWT token with claims for a given user.
     *
     * @param claims additional claims to include in the token.
     * @param user   the user for whom the token is generated.
     * @return the generated JWT token.
     */
    public String getToken(Map<String, Object> claims, UserDetails user) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 hours expiration
                .signWith(SignatureAlgorithm.HS256, getKey())
                .compact();
    }

    /**
     * Extracts the username from a JWT token.
     *
     * @param token the JWT token.
     * @return the username extracted from the token.
     */
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the token.
     *
     * @param <T>            the type of the claim.
     * @param token          the JWT token.
     * @param claimsResolver a function to resolve the claim.
     * @return the extracted claim.
     */
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Validates if the token is still valid for a given user.
     *
     * @param token       the JWT token.
     * @param userDetails the user's details.
     * @return true if the token is valid, false otherwise.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Checks if the token is expired.
     *
     * @param token the JWT token.
     * @return true if the token is expired, false otherwise.
     */
    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the token.
     *
     * @param token the JWT token.
     * @return the expiration date of the token.
     */
    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts all claims from a JWT token.
     *
     * @param token the JWT token.
     * @return the claims extracted from the token.
     */
    private Claims getAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getKey())
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Decodes the secret key to be used for signing the JWT token.
     *
     * @return the decoded secret key.
     */
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
