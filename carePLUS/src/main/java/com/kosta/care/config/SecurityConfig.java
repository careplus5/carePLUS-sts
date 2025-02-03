package com.kosta.care.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
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
    private EmployeeRepository employeeRepository;


	@Bean
	public BCryptPasswordEncoder encodePassword() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http
//		.addFilter(corsFilter) // 다른 도메인 접근 허용
//		.csrf()
//		.disable();
		System.out.println("configure");

		http.formLogin()
				.loginPage("/custom-login") // 원하는 경로로 변경
				.loginProcessingUrl("/login") // 인증 처리를 원하는 경로로 변경
				.permitAll();

		http.cors().and().csrf().disable();



//		//token
		System.out.println("token start");
		http
				.authorizeRequests()
				//		.antMatchers("/**").authenticated() //login
				.antMatchers(HttpMethod.POST, "/custom-login").permitAll()
				.anyRequest().authenticated();
//		http
//				.cors().and().csrf().disable()
//				.authorizeRequests()
//				.antMatchers(HttpMethod.POST, "/login").permitAll() // 로그인은 인증 없이 허용
//				.anyRequest().authenticated() // 나머지는 인증 필요
//				.and()
//				.addFilter(new JwtAuthenticationFilter(authenticationManager())) // 인증 필터
//				.addFilterAfter(new JwtAuthorizationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);

		System.out.println("addFilter start");
		http.addFilterBefore(new JwtAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
				.addFilterAfter(new JwtAuthorizationFilter(authenticationManager(), employeeRepository), UsernamePasswordAuthenticationFilter.class);
//

		System.out.println("token end");
		//http.addFilterAfter(new JwtAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
