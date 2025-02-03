package com.kosta.care.config.auth;

import com.kosta.care.config.jwt.JwtProperties;
import com.kosta.care.dto.EmployeeAuthDto;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthEmployeeArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isEmployeeType = EmployeeAuthDto.class.isAssignableFrom(parameter.getParameterType());
        return parameter.hasParameterAnnotation(AuthEmployee.class) &&
                isEmployeeType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        System.out.println("resolveArgument");
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        System.out.println(request.getRequestURI());
        String token = request.getHeader(JwtProperties.HEADER_STRING);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof PrincipalDetails) {
                PrincipalDetails principalDetails = (PrincipalDetails) principal;
                EmployeeAuthDto employeeAuthDto = new EmployeeAuthDto(Long.parseLong(principalDetails.getUsername()), principalDetails.getIdentity());
                return employeeAuthDto;
            }
        }

        return null;
}
    }