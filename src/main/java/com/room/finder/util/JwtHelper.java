package com.room.finder.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.room.finder.security.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtHelper {
    @Value("${jwt.secretkey}")
    private String secretKey;

    public String generateToken(CustomUserDetails customUserDetails) {
        Map<String,Object> claims=new HashMap<>();
        this.setCustomClaims(claims, customUserDetails);
        return this.doGenerateToken(claims,customUserDetails.getUsername());
    }



    private void setCustomClaims(Map<String,Object> claims,CustomUserDetails customUserDetails) {
        List<String> authorities=new ArrayList<>();
        for(GrantedAuthority auth:customUserDetails.getAuthorities()) {
            authorities.add(auth.getAuthority());

        }
        claims.put("authorities", authorities);
    }


    @SuppressWarnings("deprecation")
    private String doGenerateToken(Map<String,Object> claims,String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+(5*60*60*1000)))
                .signWith(SignatureAlgorithm.HS512,secretKey)
                .compact();

    }


    @SuppressWarnings("deprecation")
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
    }

    public <T> T getClaimFromToken(String token, Function<Claims,T> claimsResolver) {
        Claims claims=this.getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }


    public String getUserNameFromToken(String token) {
        return this.getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return this.getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        Date expiration=this.getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token,String username) {
        String userName=this.getUserNameFromToken(token);
        return org.springframework.util.StringUtils.hasText(userName) && userName.equals(username)&& !this.isTokenExpired(token);
    }


    public Authentication getAuthentication(String userName, List<GrantedAuthority> authorities, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userName, null,authorities);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        return authenticationToken;
    }


    public List<GrantedAuthority> getAuthoritiesClaimsFromToken(String token){
        Claims claims=this.getAllClaimsFromToken(token);
        List<GrantedAuthority> returnValue=null;
        @SuppressWarnings("unchecked")
        List<String> authorities=(List<String>)claims.get("authorities");
        if(authorities==null) return returnValue;

        returnValue=new ArrayList<GrantedAuthority>();
        return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());


    }

}

