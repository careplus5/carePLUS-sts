package com.kosta.care.config.jwt;

//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosta.care.config.auth.PrincipalDetails;
import com.kosta.care.entity.Employee;
import com.kosta.care.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	// UsernamePasswordAuthenticationFilter을 상속하면 attemptAutnetication을 자동으로 호출함
	// attemptAuthentication의 파라미터 HttpServletRequest request에는 로그인 정보에 대한 파라미터의 정보가 담김
	@Autowired
	private EmployeeRepository empRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager; 
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}
	
//	private JwtToken jwtToken = new JwtToken();
	
	
	@Override
	public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		 PrincipalDetails emp = (PrincipalDetails)authResult.getPrincipal();
//		   UserDetails user = (UserDetails) authResult.getPrincipal();
		 System.out.println("생기긴 했냐?");
		String accessToken = JWT.create()
                .withSubject(emp.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.ACCESS_EXPIRATION_TIME))
                .withClaim("username", emp.getUsername())
                .withClaim("paassword", emp.getPassword())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
		System.out.println("JwtAuthenticationFitler에서의 accesstoken:"+accessToken);
        String refreshToken = JWT.create()
                .withSubject(emp.getUsername())
                .withClaim("paassword", emp.getPassword())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.REFRESH_EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        response.addHeader(JwtProperties.HEADER_STRING,accessToken);
        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + refreshToken);

ObjectMapper objectMapper = new ObjectMapper();
Map<String, String> map = new HashMap<>();
map.put("accessToken", JwtProperties.TOKEN_PREFIX+accessToken);
map.put("refreshToken", JwtProperties.TOKEN_PREFIX+refreshToken);

String token = objectMapper.writeValueAsString(map);
System.out.println("JwtAuthenticationFitler에서의 token:"+token);
response.addHeader(JwtProperties.HEADER_STRING, token);
response.setContentType("application/json; charset=utf-8");

	}

}

