package com.kosta.care.config.jwt;

import java.io.IOException;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosta.care.config.auth.PrincipalDetails;
import com.kosta.care.entity.Employee;
import com.kosta.care.repository.EmployeeRepository;


// 인가: 로그인 처리가 되어야만 하는 요청이 들어왔을 실행
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
	private  final JwtToken jwtToken = new JwtToken();
	private final EmployeeRepository empRepository;
//	private EmployeeUtil empUtil;

	public JwtAuthorizationFilter (AuthenticationManager authenticationManager, EmployeeRepository empRepository) {
		super(authenticationManager);
		System.out.println("JwtAuthorization 시작");
		
		this.empRepository = empRepository;
	}

	@Override
	protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		String authentication = request.getHeader(JwtProperties.HEADER_STRING);
		String uri = request.getRequestURI();      
	      if(uri.contains("/")) {
	          chain.doFilter(request, response);
	          return;
	       }
	      System.out.println("인가 확인 중: "+uri);
	      
	      
	
	// authentication이없음 ..
	System.out.println(authentication);
	
	// token을 갖고왔는지 안 갖고왔는지의 여부를 확인해야 함
	// 토큰이 없거나 PREFIX가 Bearer가 아니거나
	

	
	ObjectMapper objectMapper = new ObjectMapper();
	// 토큰
//	Map<String, String> token = objectMapper.readValue(authentication, Map.class); // jsonstr => map
//	String token = authentication.replace(JwtProperties.TOKEN_PREFIX, "");	
	String token = authentication.replaceAll("\"", "");
	System.out.println(token.toString()+"Zz");
	
	
	//accessToken validate check
	String accessToken = authentication.replaceAll("\"", "");
	System.out.println(accessToken+"chlchlwhd");
	if(!accessToken.startsWith(JwtProperties.TOKEN_PREFIX)) {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"login need");
		return;
	}
	
	accessToken = accessToken.replace(JwtProperties.TOKEN_PREFIX,"");
	System.out.println("두번째 토큰:"+accessToken);
	
	
	// JWT 토큰검증
	try {
		System.out.println("토큰 검증 드가자");
	String id = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
			.build()
			.verify(accessToken)
			.getClaim("sub")
			.asString();
	
	System.out.println("id는 "+id);
	if(id == null || id.equals("")) throw new Exception();
	
	// 토큰에서 추출한 아이디를 사용해 사용자 정보 조회
Employee emp = empRepository.identifyJob(id);
System.out.println(emp.getName());
	
	
	
	// 인증 정보 기반으로 Spring Security의 PrincipalDetails 생성
	PrincipalDetails principalDetails = new PrincipalDetails(emp);
	
	//인증 객체 생성
	UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
	
	//Spring Security의 SecurityContextHolder에 인증 정보 저장
	SecurityContextHolder.getContext().setAuthentication(auth);
	
	// 다음 필터로 요청 전달
	chain.doFilter(request, response);
	return;
	}catch(JWTVerificationException ve) {
		ve.printStackTrace();
		try {
			String refreshToken = token;
			if(!refreshToken.startsWith(JwtProperties.TOKEN_PREFIX)) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"login need");
				return;
			}
			String id = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
					.build()
					.verify(refreshToken)
					.getClaim("sub")
					.asString();
			
			Employee emp = empRepository.identifyJob(id);
			
			String reAccessToken = jwtToken.makeAccessToken(id);
			String reRefreshToken = jwtToken.makeRefreshToken(id);
			Map<String, String> map = new HashMap<>();
			map.put("access_token", JwtProperties.TOKEN_PREFIX+reAccessToken);
			map.put("refresh_token", JwtProperties.TOKEN_PREFIX+reRefreshToken);
			
			String reToken = objectMapper.writeValueAsString(map);
			response.addHeader(JwtProperties.HEADER_STRING,reToken);
			response.setContentType("application/json; charset=utf-8");
		} catch(Exception e2 ) {
			e2.printStackTrace();
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"login need");
		}
	
	} catch(Exception e) {
		e.printStackTrace();
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"token error");
	}
	
	
	
	}

}
