package com.luv2code.ProjectManagementSystem.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;

public class JwtProvider {

     static SecretKey key = Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes());

       public static String generateToken(Authentication auth){
//   Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
            String jwts = Jwts.builder().setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime()+ 86400000))
                    .claim("email", auth.getName()).signWith(key).compact();
  return jwts;
       }

       public static String getEmailFromToken(String jwt){
           jwt = jwt.substring(7);
           Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
           String email = String.valueOf(claims.get("email"));
            return email;
       }
}
