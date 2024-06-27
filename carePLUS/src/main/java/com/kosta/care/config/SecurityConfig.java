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
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.filter.CorsFilter;

import com.kosta.care.config.jwt.JwtAuthenticationFilter;
import com.kosta.care.config.jwt.JwtAuthorizationFilter;
import com.kosta.care.repository.EmployeeRepository;

@Configuration  // IoC 빈 (bean) 등록 
@EnableWebSecurity  // 필터 체인 관리 시작 어노테이
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CorsFilter corsFilter;
	
	@Autowired
	private EmployeeRepository empRepository;
	
	@Bean
	public BCryptPasswordEncoder encodePassword() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.addFilter(corsFilter) // 다른 도메인 접근 허용 
		.csrf()
		.disable();
		
		//login
		http
			.formLogin().disable() //로그인 폼 비활성화
			.httpBasic().disable() // httpBasicdms header에 username, password를 암호화하지 않은 상태로 주고받는다. 이를 사용하지 않겠다는 의미
		.addFilterAt(new JwtAuthenticationFilter(authenticationManager()),UsernamePasswordAuthenticationFilter.class);
//		
//		//token
		http
			.addFilter(new JwtAuthorizationFilter(authenticationManager(),empRepository))
			.authorizeRequests()
	//		.antMatchers("/**").authenticated() //login
			.antMatchers(HttpMethod.POST, "/**").permitAll()
			.antMatchers(HttpMethod.GET, "/**").permitAll()
			.antMatchers(HttpMethod.POST, "/**").permitAll()
			.and()
			.formLogin()
			.loginPage("/login")
			.and()
			.logout()
			.permitAll();
		
		 http.addFilterAfter(new JwtAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
	}
}
