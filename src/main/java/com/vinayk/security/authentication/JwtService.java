package com.vinayk.security.authentication;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final String SECRET_KEY = "9402383E3A9821A94AFC5D6E6F38BA870E2D90D4110AC831E1869209BBF887549891F9672AE4C713439714F036FD1132FAACB63EC02575AC18E46F929CFD7C65E633D13E474B0CF76FDF05CE6976C03C6406470ED7196882BA5D68937E26FA87D1F387ED0F5749241019DCA119CE9707F06C94589A9A54D979B0FB264B076891";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails);
    }

    private String buildToken(Map<String, Object> objectObjectHashMap, UserDetails userDetails) {
        return Jwts
                .builder()
                .claims(objectObjectHashMap)
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 30))
                .signWith(getSignKey(), Jwts.SIG.HS256)
                .compact();

    }


    public boolean isTokenValid (String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolveClaims) {
       final Claims claims = extractAllClaims(token);
       return resolveClaims.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignKey() {
        byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }


}
