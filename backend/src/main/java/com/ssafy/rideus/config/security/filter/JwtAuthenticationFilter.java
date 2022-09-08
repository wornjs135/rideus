package com.ssafy.rideus.config.security.filter;

import com.ssafy.rideus.config.security.service.AuthService;
import com.ssafy.rideus.config.security.service.LoginUserDetails;
import com.ssafy.rideus.config.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilter {

    private final JwtUtil jwtUtil;
    private final AuthService authService;

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        String jwtToken = extractToken((HttpServletRequest) request);

        if (StringUtils.hasText(jwtToken) && jwtUtil.isValidToken(jwtToken)) {
            UserDetails userDetails = authService.loadUserByUsername(jwtUtil.getSubject(jwtToken));
            LoginUserDetails loginUserDetails = (LoginUserDetails) userDetails;

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(loginUserDetails.getMember(), null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
