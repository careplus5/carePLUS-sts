package com.kosta.care.config.auth;


import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.kosta.care.entity.Employee;

import lombok.Data;

// security가 '/loginProc' 주소를 낚아채서 로그인을 진행시킨다. 
// 로그인 진행이 완료가 되면 security session을 만들어 준다. (Security ContextHolder - 주로 시큐리티 세션이라고도 부름)
// security session에 들어가는 타입은 Authentication 타입의 객체여야 한다. 
// Authentication안에 User 정보를 넣어야 한다
// 그 User 오브젝트 타입은 UserDetails 타입이어야 한다. 
// 즉, (Security ContextHolder( new Authentication (new UserDetails ( new user ) ) )
// https://www.elancer.co.kr/blog/view?seq=235     관련 주소
@Data
public class PrincipalDetails implements UserDetails, OAuth2User {
	
	private Employee emp;  //User를 UserDetails 의 자식으로 만들어줘도 됨
	
	private BCryptPasswordEncoder passwordEncoder;
	public PrincipalDetails(Employee emp) {
		this.emp = emp;
	}
	
	private Map<String, Object> attributes;

	@Override
	public Map<String, Object> getAttributes(){
		return attributes;
	}
	
	public PrincipalDetails(Employee emp, Map<String,Object> attributes) {
		super();
		this.emp =emp;
		this.attributes = attributes;
	}
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
//		Collection<GrantedAuthority> collect = new ArrayList<>();
//		collect.add(()->user.getRoles());  // user 라서 manager 주소를 입력하면403 나옴 읽어오지 못하는 상황 
//		return collect;
//		// 유저의 권한을 collec로 만들어서 넣어줌, 스프링 시큐리티가 이렇게 만들어서 넣어줌
		return null;
	}

	@Override
	public String getPassword() {
		return emp.getPassword();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return emp.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// 우리 사이트에서 1년동안 로그인을 안하면 휴먼계정으로 변환하기로 했다면
		// 현재 시간 - 마지막 로그인 시간을 계산하여 1년 초과하면 return false
		// 테이블에 마지막 로그인 시간이 있어야 한다 (DB)
		return true;
	}
	
	public String getUsername() {
		return Long.toString(emp.getId());
	}

	
	

	

}
