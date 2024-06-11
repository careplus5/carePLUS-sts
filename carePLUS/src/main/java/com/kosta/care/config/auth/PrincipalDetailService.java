package com.kosta.care.config.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kosta.care.entity.Employee;
import com.kosta.care.repository.EmployeeRepository;
import com.kosta.care.repository.NurseRepository;
import com.kosta.care.util.EmployeeUtil;

// security 설정에서 loginProcessingUrl("/loginProc");
// '/loginProc' 요청이 오면 자동으로 UserDetailService 타입으로 IoC 되어있는 loadUserByUsername 함수가 호출된다.
// IoC가 되어잇는 이유는 서비스 어노테이션 때문 
// UserDetailSerivce와 UserDetails만 만들어주면 된다 (사이의 과정은 보이지 않음) 
@Service
public class PrincipalDetailService implements UserDetailsService  {
	
@Autowired
private EmployeeUtil empUtil;
//	@Autowired
//	private DoctorRepository docRepository;
	@Autowired
	private NurseRepository nurRepository;
//	@Autowired
//	private MedicalTechnicianRepository metRepository;
//	@Autowired
//	private AdminHospitalRepository admRepository;
	
	@Autowired
	private EmployeeRepository empRepository;

//	public Employee identifyJob(String username)throws Exception{
//	
//	}

	 @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 String identify = username.substring(0,2);
		 System.out.println("loadUserByUsername에서의 identity"+identify);
			System.out.println("loadUserByUsername에서의 username"+Long.parseLong(username));
			Employee emp = empRepository.identifyJob(username);
//		return new PrincipalDetails(emp);
		
//		 return new org.springframework.security.core.userdetails.User(Long.toString(emp.getId()), emp.getPassword(), null);
//		User user = identifyJob(username);
//	        User user = Repository.findById(username);
//	        System.out.println(user);
//	        if (user == null) {
//	            throw new UsernameNotFoundException("User not found with id: " + username);
//	        }
//// 사용자의 롤을 SimpleGrantedAuthority로 생성
//	        List<GrantedAuthority> authorities = new ArrayList<>();
//	        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN")); // 예시로 ADMIN 권한을 부여
////	        
////	        
////	        
//	        return new org.springframework.security.core.userdetails.UserDetails(emp.getId(), emp.getPassword(),authorities);
	 return new PrincipalDetails(emp); 
	 }
	
	

}
