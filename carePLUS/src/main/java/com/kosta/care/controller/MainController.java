package com.kosta.care.controller;
import com.kosta.care.config.jwt.JwtProperties;
import com.kosta.care.dto.LoginEmployeeDto;
import com.kosta.care.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.bind.annotation.*;

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

@RestController
@RequiredArgsConstructor
public class MainController {

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


	private final AuthService authService;



	@PostMapping("/custom-login")
	public ResponseEntity<?> login(@RequestBody LoginEmployeeDto loginDto) {
		System.out.println("login controller");
		String jwt = authService.authenticateAndGenerateToken(loginDto);
		return ResponseEntity.ok(JwtProperties.TOKEN_PREFIX+jwt);
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

	
	@Secured("ROLE_MANAGER")  // 권한이 매니저인 사람들만 특정 몇명 없을 경우에 쓰면 좋음 
//	@PreAuthorize("hasRole('Role_MANAGER')") secured는 or를 쓸 수 없지만 PreAuthorize 는 or를 쓸 수 있음
	@GetMapping("/manager")
	@ResponseBody
	public String manager() {
		return "매니저입니다.";
	}
	
	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_NURSE')")
	@GetMapping("/admin")
	@ResponseBody
	public String admin() {
		return "관리자(어드민)입니다.";
	}
}
