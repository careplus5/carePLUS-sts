package com.kosta.care.config.auth;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.kosta.care.entity.Role;
import com.kosta.care.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
	private String id;
	private String identity;
	private Employee employee;


	public PrincipalDetails(Employee emp) {
		employee = emp;
		id = Long.toString(emp.getId());
		identity = id.substring(0,2);
	}

	public String getUsername() {
		return id;
	}


	private Map<String, Object> attributes;

	@Override
	public Map<String, Object> getAttributes(){
		return attributes;
	}



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		String role = Role.getRoleByIdentity(identity);
		authorities.add(new SimpleGrantedAuthority("ROLE_USER")); // 기본 권한 설정
		if (identity != null) {
			authorities.add(new SimpleGrantedAuthority("ROLE_"+role)); // 기본 권한 설정
		}
		return authorities;
//		return null;
	}

	@Override
	public String getPassword() {
		return employee.getPassword();
	}

	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return null;
	}

	public String getIdentity() {
		return identity;
	}

	@Override
	public boolean isAccountNonExpired() {
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
		return true;
	}

	public Employee getEmployee() {
		return employee;
	}

	

}
