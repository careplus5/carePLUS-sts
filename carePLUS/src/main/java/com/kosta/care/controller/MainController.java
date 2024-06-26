package com.kosta.care.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosta.care.config.auth.PrincipalDetails;
import com.kosta.care.config.jwt.JwtToken;
import com.kosta.care.entity.AdminHospital;
import com.kosta.care.entity.Doctor;
import com.kosta.care.entity.Employee;
import com.kosta.care.entity.MedicalTechnician;
import com.kosta.care.entity.Nurse;
import com.kosta.care.repository.AdminHospitalRepository;
import com.kosta.care.repository.DoctorRepository;
import com.kosta.care.repository.EmployeeRepository;
import com.kosta.care.repository.MedicalTechnicianRepository;
import com.kosta.care.repository.NurseRepository;

@Controller
public class MainController {
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	@Autowired
	private AdminHospitalRepository admRepository;
	@Autowired
	private EmployeeRepository empRepository;
	@Autowired
	private NurseRepository nurRepository;
	@Autowired
	private DoctorRepository docRepository;
	@Autowired
	private MedicalTechnicianRepository metRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
  

//	
	@GetMapping("/")  // 두 개 이상의 매핑은 { }로 감싼다
	@ResponseBody
	public String index() {
		return "index";
	}


	

@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody Employee emp, JwtToken jwtToken, AuthenticationManager authenticationManager){
	System.out.println("로그인 과정 시작~");
	try {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(emp.getId(),emp.getPassword()));
	SecurityContextHolder.getContext().setAuthentication(authentication);
	
	String jwt = jwtToken.makeAccessToken(Long.toString(emp.getId()));
	return ResponseEntity.ok().header("Authorization", "Bearer "+jwt).build();
	} catch(AuthenticationException e) {
		e.printStackTrace();
		return ResponseEntity.badRequest().body("로그인 실패");
	}
}

	
	@PostMapping("/joinProc")
	public String joinProc(Employee emp) {
		System.out.println("회원가입 진행 : " + emp);
	// 시큐리티는 반드시 밀번호가 암호화해서 들어감 
		String rawPassword = emp.getPassword();
	String encodePassword = bCryptPasswordEncoder.encode(rawPassword);
		// BCryptPasswordEncoder 이것을 만들어 주었음 config 패키에다
		// Using generated security password: a13ff4b5-9ffb-46a8-8f7f-120b3534f961
		// 콘솔에서 보면 위와 같은 것이 있음 이것이 암호화 한 것
	emp.setPassword(encodePassword);
		empRepository.save(emp);
		return "redirect:/";
	}
	
	@PostMapping("/joinDocProc")
	public String joinDocProc(Doctor emp) {
		System.out.println("회원가입 진행 : " + emp);
	// 시큐리티는 반드시 밀번호가 암호화해서 들어감 
		String rawPassword = emp.getPassword();
	String encodePassword = bCryptPasswordEncoder.encode(rawPassword);
		// BCryptPasswordEncoder 이것을 만들어 주었음 config 패키에다
		// Using generated security password: a13ff4b5-9ffb-46a8-8f7f-120b3534f961
		// 콘솔에서 보면 위와 같은 것이 있음 이것이 암호화 한 것
	emp.setPassword(encodePassword);
		docRepository.save(emp);
		return "redirect:/";
	}
	
	@PostMapping("/joinMetProc")
	public String joinMetProc(MedicalTechnician emp) {
		System.out.println("회원가입 진행 : " + emp);
	// 시큐리티는 반드시 밀번호가 암호화해서 들어감 
		String rawPassword = emp.getPassword();
	String encodePassword = bCryptPasswordEncoder.encode(rawPassword);
		// BCryptPasswordEncoder 이것을 만들어 주었음 config 패키에다
		// Using generated security password: a13ff4b5-9ffb-46a8-8f7f-120b3534f961
		// 콘솔에서 보면 위와 같은 것이 있음 이것이 암호화 한 것
	emp.setPassword(encodePassword);
		metRepository.save(emp);
		return "redirect:/";
	}
	
	@PostMapping("/joinAdmProc")
	public String joinAdmProc(AdminHospital emp) {
		System.out.println("회원가입 진행 : " + emp);
	// 시큐리티는 반드시 밀번호가 암호화해서 들어감 
		String rawPassword = emp.getPassword();
	String encodePassword = bCryptPasswordEncoder.encode(rawPassword);
	emp.setPassword(encodePassword);
	admRepository.save(emp);
		return "redirect:/";
	}
	
	@PostMapping("/joinNurProc")
	public String joinNurProc(Nurse emp) {
		System.out.println("회원가입 진행 : " + emp);
		String rawPassword = emp.getPassword();
	String encodePassword = bCryptPasswordEncoder.encode(rawPassword);

	emp.setPassword(encodePassword);
		nurRepository.save(emp);
		return "redirect:/";
	}
 	
	@GetMapping("/emp")
	@ResponseBody
	public String user(@AuthenticationPrincipal PrincipalDetails principal) {  // 시큐리티 코어 어노테이
		System.out.println(principal.getEmp());
		return "유저입니다.";
	}
	
	@Secured("ROLE_MANAGER")  // 권한이 매니저인 사람들만 특정 몇명 없을 경우에 쓰면 좋음 
//	@PreAuthorize("hasRole('Role_MANAGER')") secured는 or를 쓸 수 없지만 PreAuthorize 는 or를 쓸 수 있음
	@GetMapping("/manager")
	@ResponseBody
	public String manager() {
		return "매니저입니다.";
	}
	
	@PreAuthorize("hasRole('Role_MANAGER') or hasRole('ROLE_ADMIN')")
	@GetMapping("/admin")
	@ResponseBody
	public String admin() {
		return "관리자(어드민)입니다.";
	}
}
