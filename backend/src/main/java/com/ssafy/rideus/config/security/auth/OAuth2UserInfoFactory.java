package com.ssafy.rideus.config.security.auth;

import com.ssafy.rideus.config.security.auth.company.GoogleOAuth2UserInfo;
import com.ssafy.rideus.config.security.auth.company.KakaoOAuth2UserInfo;
import com.ssafy.rideus.config.security.auth.company.NaverOAuth2UserInfo;
import com.ssafy.rideus.domain.type.AuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(AuthProvider authProvider, Map<String, Object> attributes) {
        switch (authProvider) {
            case GOOGLE: return new GoogleOAuth2UserInfo(attributes);
            case KAKAO: return new KakaoOAuth2UserInfo(attributes);
            case NAVER: return new NaverOAuth2UserInfo(attributes);
            default: throw new IllegalArgumentException("Invalid Provider Type.");
        }
    }
}
