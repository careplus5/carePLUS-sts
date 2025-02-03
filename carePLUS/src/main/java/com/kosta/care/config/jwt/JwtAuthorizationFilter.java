package com.kosta.care.config.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.kosta.care.entity.Employee;
import com.kosta.care.entity.Nurse;
import com.kosta.care.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosta.care.config.auth.PrincipalDetails;


// 인가: 로그인 처리가 되어야만 하는 요청이 들어왔을 실행
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
	private  final JwtToken jwtToken = new JwtToken();
	private final EmployeeRepository employeeRepository;


	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, EmployeeRepository employeeRepository) {
		// 부모 클래스의 생성자에 AuthenticationManager를 전달
		super(authenticationManager);
		this.employeeRepository = employeeRepository;
		System.out.println("author manager");
	}

	@Override
	protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//		System.out.println("doFilterInternal");
//		String authentication = request.getHeader(JwtProperties.HEADER_STRING);
//
//
//		String jwtHeader = request.getHeader("Authorization");
//
//		if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
//			chain.doFilter(request, response);
//			return;
//		}
//
//	ObjectMapper objectMapper = new ObjectMapper();
//
//	String token = authentication.replaceAll("\"", "");
//		token = token.replace(JwtProperties.TOKEN_PREFIX,"");
//
//
//	try {
//		System.out.println("인증 시작");
//	String id = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
//			.build()
//			.verify(token)
//			.getClaim("id")
//			.asString();
//
//	System.out.println("id는 "+id);
//	if(id == null || id.equals("")) throw new Exception();
//
//	PrincipalDetails principalDetails = new PrincipalDetails(id);
//
//	//인증 객체 생성
//	UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
//
//	SecurityContextHolder.getContext().setAuthentication(auth);
//		String identity = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
//				.build()
//				.verify(token)
//				.getClaim("identity")
//				.asString();
//	chain.doFilter(request, response);
//	return;
//	}catch(JWTVerificationException ve) {
//		ve.printStackTrace();
//		try {
//			String refreshToken = token;
//			if(!refreshToken.startsWith(JwtProperties.TOKEN_PREFIX)) {
//				response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"login need");
//				return;
//			}
//			String id = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
//					.build()
//					.verify(refreshToken)
//					.getClaim("sub")
//					.asString();
//
//
//			String reAccessToken = jwtToken.makeAccessToken(id);
//			String reRefreshToken = jwtToken.makeRefreshToken(id);
//			Map<String, String> map = new HashMap<>();
//			map.put("access_token", JwtProperties.TOKEN_PREFIX+reAccessToken);
//			map.put("refresh_token", JwtProperties.TOKEN_PREFIX+reRefreshToken);
//
//			String reToken = objectMapper.writeValueAsString(map);
//			response.addHeader(JwtProperties.HEADER_STRING,reToken);
//			response.setContentType("application/json; charset=utf-8");
//		} catch(Exception e2) {
//			e2.printStackTrace();
//			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"login need");
//		}
//
//	} catch(Exception e) {
//		e.printStackTrace();
//		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"token error");
//	}


		System.out.println("doFilterInternal");
		String jwtHeader = request.getHeader("Authorization");

		if(jwtHeader == null || !jwtHeader.startsWith("Bearer ")) {
			System.out.println("dont~");
			chain.doFilter(request, response);
			return;
		}

		try {
			String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
			DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
					.build()
					.verify(jwtToken);

			String id = decodedJWT.getClaim("id").asString();
			Employee employee = employeeRepository.identifyJob(id);

			PrincipalDetails principalDetails = new PrincipalDetails(employee);
			Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (JWTVerificationException e) {
			System.out.println("[JwtAuthorizationFilter] token 파싱 실패: : {}");
		}
		chain.doFilter(request, response);
	}

}
