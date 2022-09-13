package com.ssafy.rideus.config.security.auth.company;


import com.ssafy.rideus.config.security.auth.OAuth2UserInfo;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getName() {
        Map<String, Object> properties = getProperties("properties");

        if (properties == null) {
            return null;
        }

        return (String) properties.get("nickname");
    }

    @Override
    public String getEmail() {
        Map<String, Object> properties = getProperties("kakao_account");
        if (properties == null) {
            return null;
        }
        return (String) properties.get("email");
    }

    @Override
    public String getImageUrl() {
        Map<String, Object> properties = getProperties("properties");

        if (properties == null) {
            return null;
        }

        return (String) properties.get("thumbnail_image");
    }

    private Map<String, Object> getProperties(String properties) {
        return (Map<String, Object>) attributes.get(properties);
    }
}
