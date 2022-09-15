package com.ssafy.rideus.config.web;

import com.ssafy.rideus.common.exception.NotMatchException;
import com.ssafy.rideus.repository.jpa.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.ssafy.rideus.common.exception.NotMatchException.TOKEN_NOT_MATCH;

@Component
@RequiredArgsConstructor
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginMember.class) != null;
        boolean isLongClass = Member.class.equals(parameter.getParameterType());
        return isLoginUserAnnotation && isLongClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return (Member) authentication.getPrincipal();
        } catch (ClassCastException e) {
            throw new NotMatchException(TOKEN_NOT_MATCH);
        }
    }

}
