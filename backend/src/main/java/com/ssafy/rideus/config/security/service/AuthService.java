package com.ssafy.rideus.config.security.service;

import com.ssafy.rideus.common.exception.NotFoundException;
import com.ssafy.rideus.config.security.auth.CustomUserDetails;
import com.ssafy.rideus.config.security.util.CookieUtil;
import com.ssafy.rideus.config.security.util.JwtTokenProvider;
import com.ssafy.rideus.domain.Member;
import com.ssafy.rideus.repository.jpa.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static com.ssafy.rideus.common.exception.NotFoundException.USER_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    @Value("${token.refresh-cookie-key}")
    private String cookieKey;

    private final MemberRepository memberRepository;
    private final JwtTokenProvider tokenProvider;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member findMember = memberRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(String.valueOf(findMember.getRole())));

        LoginUserDetails loginUserDetails = new LoginUserDetails(String.valueOf(findMember.getId()), "", authorities);
        loginUserDetails.setMember(findMember);
        return loginUserDetails;
    }

    public String refreshToken(HttpServletRequest request, HttpServletResponse response, String oldAccessToken) {
        // 1. Validation Refresh Token
        String oldRefreshToken = CookieUtil.getCookie(request, cookieKey)
                .map(Cookie::getValue).orElseThrow(() -> new RuntimeException("no Refresh Token Cookie"));

        if (!tokenProvider.validateToken(oldRefreshToken)) {
            throw new RuntimeException("Not Validated Refresh Token");
        }

        // 2. 유저정보 얻기
        Authentication authentication = tokenProvider.getAuthentication(oldAccessToken);
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        Long id = user.getId();

        // 3. Match Refresh Token
        String savedToken = memberRepository.getRefreshTokenById(id);

        if (!savedToken.equals(oldRefreshToken)) {
            throw new RuntimeException("Not Matched Refresh Token");
        }

        // 4. JWT 갱신
        String accessToken = tokenProvider.createAccessToken(authentication);
        tokenProvider.createRefreshToken(authentication, response);

        return accessToken;
    }

}
