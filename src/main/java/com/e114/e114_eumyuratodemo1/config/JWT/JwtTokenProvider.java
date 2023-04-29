package com.e114.e114_eumyuratodemo1.config.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

// 토큰을 생성하고 검증하는 클래스입니다.
// 해당 컴포넌트는 필터클래스에서 사전 검증을 거칩니다.
@RequiredArgsConstructor
@Service
public class JwtTokenProvider {

    // JWT 암호화에 사용될 비밀키
    private String secretKey = "webfirewood";

    // JWT 토큰 유효시간 설정 (30분)
    private long tokenValidTime = 30 * 60 * 1000L;

    private final UserDetailsService userDetailsService;

    // 객체 초기화 시, 비밀키를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성 메소드
    public String createToken(String userPk, List<String> roles) {
        // 토큰 내에 넣을 클레임 정보 생성
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);
        Date now = new Date();
        // 토큰 생성 및 반환
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회 메소드
    public Authentication getAuthentication(String token) {
        // JWT 토큰에서 회원 정보를 추출하여 UserDetails 객체 생성
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        // UserDetails 객체와 권한 정보를 가지고, Authentication 객체를 생성하여 반환
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // JWT 토큰에서 회원 정보 추출 메소드
    public String getUserPk(String token) {
        // 토큰 파싱 후, 회원 아이디 추출하여 반환
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 JWT 토큰 값을 가져오는 메소드
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        // 헤더에서 Authorization 값을 찾아 Bearer로 시작하면, 토큰 값 반환
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // JWT 토큰의 유효성과 만료일자 확인 메소드
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            // 토큰의 만료일자가 현재 시간보다 이전이면, 유효하지 않은 토큰으로 처리
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            // 예외가 발생하면 유효하지 않은 토큰으로 처리
            return false;
        }
    }

    public Date getExpirationDateFromToken (String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration();
    }
}


