package com.kosta.care.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import com.kosta.care.config.jwt.JwtAuthenticationFilter;
import com.kosta.care.config.jwt.JwtAuthorizationFilter;
import com.kosta.care.repository.EmployeeRepository;

@Configuration  // IoC 빈 (bean) 등록 
@EnableWebSecurity  // 필터 체인 관리 시작 어노테이
// 컨트롤러로 가기 전에 가로 채서 
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	// extends 는 클래스 (broswer)
	// include 는 인터페이스 (add interface \)
	
	@Autowired
	private CorsFilter corsFilter;
	
	@Autowired
	private EmployeeRepository empRepository;
	
	@Bean
	public BCryptPasswordEncoder encodePassword() {
		return new BCryptPasswordEncoder();
	}
	
	// Using generated security password: 6b03047f-0587-4454-97bb-1e4907502b67 이것이 비밀번호
	// 아이디 : user 
	// 로그인 하면 세션처리가 된다 
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilter(corsFilter) // 다른 도메인 접근 허용 
		.csrf().disable(); // csrf 공격 비활성화
		
		//login
		http
			.formLogin().disable() //로그인 폼 비활성화
			.httpBasic().disable() // httpBasicdms header에 username, password를 암호화하지 않은 상태로 주고받는다. 이를 사용하지 않겠다는 의미
		.addFilterAt(new JwtAuthenticationFilter(authenticationManager()),UsernamePasswordAuthenticationFilter.class);
		
//		//Oauth2 Login
//		http
//			.oauth2Login()
//			.authorizationEndpoint().baseUri("/oauth2/authorization")
//			.and()
//			.redirectionEndpoint().baseUri("/oauth2/callback/*")
//			.and()
//			.userInfoEndpoint().userService(null);
//		
		//token
		http
			.addFilter(new JwtAuthorizationFilter(authenticationManager(),empRepository))
			.authorizeRequests()
//			.antMatchers("/**").authenticated() //login 
			.antMatchers(HttpMethod.GET, "/**").permitAll()
			.and()
			.formLogin()
			.loginPage("/login")
			.and()
			.logout()
			.permitAll();
		
		 http.addFilterAfter(new JwtAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
	}
}
