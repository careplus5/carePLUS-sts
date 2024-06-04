package com.kosta.care.config.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosta.care.config.auth.PrincipalDetails;
import com.kosta.care.entity.Employee;
import com.kosta.care.repository.EmployeeRepository;
import com.kosta.care.util.EmployeeUtil;


// 인가: 로그인 처리가 되어야만 하는 요청이 들어왔을 실행
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
	public JwtAuthorizationFilter (AuthenticationManager authenticationManager, EmployeeRepository empRepository) {
		super(authenticationManager);
		this.empRepository = empRepository;
	}
	
	private JwtToken jwtToken = new JwtToken();
	private EmployeeRepository empRepository;
	private EmployeeUtil empUtil;
	
	@Override
	protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		//super.doFilterInternal(request, response, chain);
	String uri = request.getRequestURI(); //로그인이 필요없으면 그냥 흐르게 

	// 로그인(인증)이 필요없는 요청은 그대로 진행
	if(!(uri.contains("/employee") || uri.contains("/admin") || uri.contains("/manager"))) {
		chain.doFilter(request, response);
		return;
	}
	
	String authentication = request.getHeader(JwtProperties.HEADER_STRING);
	// token을 갖고왔는지 안 갖고왔는지의 여부를 확인해야 함
	// 토큰이 없거나 PREFIX가 Bearer가 아니거나
	if(authentication==null) {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"로그인 필요");
		return;
	}
	
	ObjectMapper objectMapper = new ObjectMapper();
	Map<String, String> token = objectMapper.readValue(authentication, Map.class); // jsonstr => map
	System.out.println(token);
 	
	//accessToken validate check
	String access_token = token.get("access_token");
	if(!access_token.startsWith(JwtProperties.TOKEN_PREFIX)) {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"login need");
		return;
	}
	
	access_token = access_token.replace(JwtProperties.TOKEN_PREFIX,"");
	try {
	String id = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
			.build()
			.verify(access_token)
			.getClaim("sub")
			.asString();
	
	System.out.println(id);
	if(id == null || id.equals("")) throw new Exception();
	
Employee emp = empRepository.identifyJob(id);
	if(emp == null) throw new Exception();
	
	PrincipalDetails principalDetails = new PrincipalDetails(emp);
	UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
	SecurityContextHolder.getContext().setAuthentication(auth);
	chain.doFilter(request, response);
	return;
	}catch(JWTVerificationException ve) {
		ve.printStackTrace();
		try {
			String refresh_token = token.get("refresh_token");
			if(!refresh_token.startsWith(JwtProperties.TOKEN_PREFIX)) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"login need");
				return;
			}
			String id = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
					.build()
					.verify(refresh_token)
					.getClaim("sub")
					.asString();
			
			Employee emp = empRepository.identifyJob(id);
			
			String reAccess_token = jwtToken.makeAccessToken(id);
			String reRefresh_token = jwtToken.makeRefreshToken(id);
			Map<String, String> map = new HashMap<>();
			map.put("access_token", JwtProperties.TOKEN_PREFIX+reAccess_token);
			map.put("refresh_token", JwtProperties.TOKEN_PREFIX+reRefresh_token);
			
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
