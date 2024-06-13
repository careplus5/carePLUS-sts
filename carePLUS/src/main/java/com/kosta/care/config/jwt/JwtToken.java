package com.kosta.care.config.jwt;

import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.kosta.care.config.auth.PrincipalDetails;

import io.jsonwebtoken.Jwts;

@Component
public class JwtToken {
	
	public String makeAccessToken(String id) {
		return JWT.create()
				.withSubject(id)
				.withIssuedAt(new Date(System.currentTimeMillis()))
				//.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.ACCESS_EXPIRATION_TIME))
				.sign(Algorithm.HMAC512(JwtProperties.SECRET));
		
	}
	
	public String makeRefreshToken(String id) {
		return JWT.create().withSubject(id).withIssuedAt(new Date(System.currentTimeMillis()))
				.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.REFRESH_EXPIRATION_TIME))
				.sign(Algorithm.HMAC512(JwtProperties.SECRET));
	}


	    public String generateToken(Authentication authentication) {
	        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
	        System.out.println(principalDetails.getUsername()+"님의 토큰 생성 완료.");

	        return Jwts.builder()
	                .setSubject(principalDetails.getUsername())
	                .setIssuedAt(new Date())
	                .setExpiration(new Date(System.currentTimeMillis()+JwtProperties.REFRESH_EXPIRATION_TIME))
	                .compact();
	    }

}
