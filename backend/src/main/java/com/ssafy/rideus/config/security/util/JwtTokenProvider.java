package com.ssafy.rideus.config.security.util;

import com.ssafy.rideus.config.security.auth.CustomUserDetails;
import com.ssafy.rideus.config.security.service.AuthService;
import com.ssafy.rideus.config.security.service.LoginUserDetails;
import com.ssafy.rideus.repository.jpa.MemberRepository;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenProvider {

    private final String SECRET_KEY;
    private final String COOKIE_REFRESH_TOKEN_KEY;

    @Value("${token.expiration_time}")
    private long ACCESS_TOKEN_EXPIRE_LENGTH;
    private final Long REFRESH_TOKEN_EXPIRE_LENGTH = 1000L * 60 * 60 * 24 * 7;    // 1week
    private final String AUTHORITIES_KEY = "role";


    private final MemberRepository memberRepository;

    private final AuthService authService;

    @Autowired
    public JwtTokenProvider(@Value("${token.secret}") String secretKey, @Value("${token.refresh-cookie-key}") String cookieKey, AuthService authService, MemberRepository memberRepository) {
        this.SECRET_KEY = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.COOKIE_REFRESH_TOKEN_KEY = cookieKey;
        this.authService = authService;
        this.memberRepository = memberRepository;
    }

    public String createAccessToken(Authentication authentication) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_LENGTH);

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        String userId = user.getName();
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setSubject(userId)
                .claim(AUTHORITIES_KEY, role)
                .setIssuer("ride-us")
                .setIssuedAt(now)
                .setExpiration(validity)
                .compact();
    }

    public void createRefreshToken(Authentication authentication, HttpServletResponse response) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_LENGTH);

        String refreshToken = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setIssuer("ride-us")
                .setIssuedAt(now)
                .setExpiration(validity)
                .compact();

        saveRefreshToken(authentication, refreshToken);

        ResponseCookie cookie = ResponseCookie.from(COOKIE_REFRESH_TOKEN_KEY, refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .maxAge(REFRESH_TOKEN_EXPIRE_LENGTH / 1000)
                .path("/")
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    private void saveRefreshToken(Authentication authentication, String refreshToken) {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        Long id = Long.valueOf(user.getName());

        memberRepository.updateRefreshToken(id, refreshToken);
    }

    // Access Token을 검사하고 얻은 정보로 Authentication 객체 생성
    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);


        UserDetails userDetails = authService.loadUserByUsername(claims.getId());
//        Collection<? extends GrantedAuthority> authorities =
//                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
//                        .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        LoginUserDetails loginUserDetails = (LoginUserDetails) userDetails;


        return new UsernamePasswordAuthenticationToken(loginUserDetails.getMember(), null, userDetails.getAuthorities());

    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalStateException e) {
            log.info("JWT 토큰이 잘못되었습니다");
        }
        return false;
    }

    // Access Token 만료시 갱신때 사용할 정보를 얻기 위해 Claim 리턴
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}