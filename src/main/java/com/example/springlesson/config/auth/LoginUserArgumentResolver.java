package com.example.springlesson.config.auth;

import com.example.springlesson.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@Component
@RequiredArgsConstructor
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter ( MethodParameter parameter ) {
        boolean hasLoginUserAnnotation = parameter.getParameterAnnotation ( LoginUser.class ) != null;
        boolean isSessionUserClass = SessionUser.class.equals ( parameter.getParameterType ( ) );
        return hasLoginUserAnnotation && isSessionUserClass;
    }

    @Override
    public Object resolveArgument ( MethodParameter parameter,
                                    ModelAndViewContainer mavContainer,
                                    NativeWebRequest webRequest,
                                    WebDataBinderFactory binderFactory ) throws Exception {
        return httpSession.getAttribute ( "user" );
    }
}
