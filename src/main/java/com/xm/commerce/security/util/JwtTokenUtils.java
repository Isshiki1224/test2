package com.xm.commerce.security.util;

import com.xm.commerce.security.constant.SecurityConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenUtils {

    @Value("${jwt.secretKey}")
    private String secretKeyStr;

    public String createToken(String username, List<String> roles) {
        SecretKey secretKey = Keys.hmacShaKeyFor(DatatypeConverter.parseBase64Binary(secretKeyStr));
        long expiration = SecurityConstant.TOKEN_EXPIRATION;
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expiration * 1000);
        String tokenSuffix = Jwts.builder()
                .setHeaderParam("type", SecurityConstant.TOKEN_TYPE)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .claim(SecurityConstant.ROLE_CLAIMS, String.join(",", roles))
                .setIssuedAt(createdDate)
                .setSubject(username)
                .setExpiration(expirationDate)
                .compact();
        return SecurityConstant.TOKEN_PREFIX + tokenSuffix;
    }

    public boolean isTokenExpired(String token) {
        Date expiredDate = getTokenBody(token).getExpiration();
        return expiredDate.before(new Date());
    }

    public String getUsernameByToken(String token) {
        return getTokenBody(token).getSubject();
    }

    private Claims getTokenBody(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(DatatypeConverter.parseBase64Binary(secretKeyStr));
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
