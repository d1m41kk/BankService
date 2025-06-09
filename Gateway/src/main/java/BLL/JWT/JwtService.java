package BLL.JWT;

import Security.Config.ATMEntityDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    private final SecretKey secretKey;
    private final JwtParser jwtParser;

    public JwtService() {
        String secret = "NjS+NQ4Hdac/GPkhl08hNKILqu7hflrAd0rH3whpFb5fhqt8W2MwyycAUXzhktGGrkJzPZJpYg7+XAJoUyoamg==";
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);

        this.jwtParser = Jwts.parser()
                .verifyWith(secretKey)
                .build();
    }

    public String generateToken(ATMEntityDetails entityDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", entityDetails.getRole());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(entityDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractLogin(String token) {
        return jwtParser.parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            String username = extractLogin(token);
            Date expiration = jwtParser.parseClaimsJws(token).getBody().getExpiration();
            return username.equals(userDetails.getUsername())
                    && expiration.after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
