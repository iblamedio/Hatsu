package com.meddle.Hatsu.Auth;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class AuthUtils {

   private final String SECRET = "91782638912Y39U12H49821631U9H4O12Y391UOIKH3412UI";

   private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

   private final Long EXPIRES_IN = 1000 * 60 * 60 * 10L;

   public String generateToken(String username) {
      return Jwts.builder().setIssuer("Hatsu").setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRES_IN)).setSubject(username)
            .signWith(key)
            .compact();
   }

   public String extractUsername(String token) {
      return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
   }

   public boolean isTokenValid(String token, String username) {
      if (!extractUsername(token).equals(username)) {
         return false;
      }

      return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration()
            .after(new Date(System.currentTimeMillis()));
   }
}
