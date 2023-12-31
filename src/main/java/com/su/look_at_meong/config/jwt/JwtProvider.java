package com.su.look_at_meong.config.jwt;

import com.su.look_at_meong.model.member.constant.Role;
import com.su.look_at_meong.model.member.dto.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Getter
@Setter
public class JwtProvider {

    private final Key key;
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";
    private static final String CLAIM_KEY = "email";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; // access 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // refresh 7일
    private static final String ROLE = "MEMBER";

    public JwtProvider(@Value("${spring.jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto generateTokenDto(String email) {

        long now = (new Date()).getTime();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
            .setSubject(email) //payload "sub" : "name"
            .claim(AUTHORITIES_KEY, Role.MEMBER) //payload "auth" : "ROLE_USER"
            .setExpiration(accessTokenExpiresIn) //payload "exp" : 1234567890 (10자리)
            .signWith(key, SignatureAlgorithm.HS512) //header "alg" : HS512 (해싱 알고리즘 HS512)
            .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
            .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();

        return TokenDto.builder()
            .grantType(BEARER_TYPE)
            .accessToken(accessToken)
            .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
            .refreshToken(refreshToken)
            .build();
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String accessToken) {

        return new UsernamePasswordAuthenticationToken(getEmail(accessToken), null,
            List.of(new SimpleGrantedAuthority(ROLE)));
    }

    public String getEmail(String token) {
        return parseClaims(token).get(CLAIM_KEY, String.class);
    }

    public long getExpiration(String token) {
        long time = System.currentTimeMillis();
        Date expiration = parseClaims(token).getExpiration();
        return expiration.getTime() - time;
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("JWT 서명의 형식이 잘못되었습니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원하지 않는 JWT 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("잘못된 JWT 입니다.");
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
        }catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
