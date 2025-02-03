package com.kosta.care.service;

import com.kosta.care.config.jwt.JwtToken;
import com.kosta.care.dto.LoginEmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtToken jwtToken;

    public String authenticateAndGenerateToken(LoginEmployeeDto loginEmployeeDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginEmployeeDto.getUsername(), loginEmployeeDto.getPassword())
        );

        System.out.println(authentication.getAuthorities()+"입니당.");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtToken.makeAccessToken(loginEmployeeDto.getUsername());
        return jwt;
    }
}
