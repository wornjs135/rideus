package com.ssafy.rideus.config.security.service;

import com.ssafy.rideus.repository.jpa.domain.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Setter
@Getter
public class LoginUserDetails extends User {

    private Member member;

    public LoginUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
