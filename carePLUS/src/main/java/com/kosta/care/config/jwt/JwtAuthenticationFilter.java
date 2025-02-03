package com.kosta.care.config.jwt;

//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.kosta.care.config.auth.PrincipalDetails;



public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	// UsernamePasswordAuthenticationFilter을 상속하면 attemptAutnetication을 자동으로 호출함
	// attemptAuthentication의 파라미터 HttpServletRequest request에는 로그인 정보에 대한 파라미터의 정보가 담김

	private final AuthenticationManager authenticationManager;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
		this.authenticationManager = authenticationManager;
		System.out.println("authen manager");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		return super.attemptAuthentication(request, response);
	}
	
	@Override
	public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		PrincipalDetails emp = (PrincipalDetails)authResult.getPrincipal();
		String accessToken = JWT.create()
                .withSubject(emp.getId())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.ACCESS_EXPIRATION_TIME))
                .withClaim("id", emp.getId())
				.withClaim("identity", emp.getIdentity())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        String refreshToken = JWT.create()
                .withSubject(emp.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.REFRESH_EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));


        response.addHeader(JwtProperties.HEADER_STRING,JwtProperties.TOKEN_PREFIX + accessToken);
        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + refreshToken);


		System.out.println("jwt:"+accessToken);
response.addHeader(JwtProperties.HEADER_STRING, accessToken);
response.setContentType("application/json; charset=utf-8");

	}

}

