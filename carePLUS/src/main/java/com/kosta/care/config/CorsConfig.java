package com.kosta.care.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kosta.care.config.jwt.JwtProperties;

@Configuration
public class CorsConfig implements WebMvcConfigurer{
	   @Override
	   public void addCorsMappings(CorsRegistry registry) {
	      registry.addMapping("/**")
	         .allowedOrigins("http://localhost:3000")
	         .allowedMethods("GET","POST","PUT","DELETE") .allowedHeaders("*")
             .allowCredentials(true)
             .exposedHeaders("Authorization");
	   }
	
	

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		//config.addAllowedOrigin("http://localhost:3000");localhost:3000 주소만 허용
		config.addAllowedOriginPattern("*");
		//모든 패턴을 허용
		config.addAllowedHeader("*");
		//모든 헤더를 허용, Access-Control-Allow-Headers 
		//자스 헤더 영역
		//토큰에 대한 정보를 계속 주고받을 거라서 씀 
		config.addAllowedMethod("*");
		// get, post, delete 등 모든 메소드 허용
		// Access-Control-Allow-Method
		config.addExposedHeader(JwtProperties.HEADER_STRING);
		//client(react, view... etc)가 응답에 접근할 수 있는 header 추가
		source.registerCorsConfiguration("/*", config);
		source.registerCorsConfiguration("/*/*", config);
		return new CorsFilter(source);
		
		
	}
}
