package com.kosta.care.config.jwt;

public interface JwtProperties {
	String SECRET = "kosta"; // 우리 서버의 고유 비밀키
	int ACCESS_EXPIRATION_TIME = 60000*60*2; // 만료 시간, 2시간
	int REFRESH_EXPIRATION_TIME = 60000*60*24; //24시간
	String TOKEN_PREFIX = "Bearer "; // 공백 필수,토큰 방식을 의미
	String HEADER_STRING = "Authorization"; 
	
	

}
