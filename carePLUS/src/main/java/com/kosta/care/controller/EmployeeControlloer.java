package com.kosta.care.controller;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.care.dto.EmployeeDto;
import com.kosta.care.service.EmployeeSerivce;
import com.kosta.care.util.PageInfo;

@RestController
public class EmployeeControlloer {

	@Autowired
	private EmployeeSerivce employeeSerivce;

	@Value("${upload.profile}")
	private String uploadProfile;
	@Value("${upload.file}")
	private String uploadFile;

	@PostMapping("/employeeAdd")
	public ResponseEntity<String> employeeAdd(@ModelAttribute EmployeeDto employeeDto,
			@RequestParam(name = "file", required = false) MultipartFile file) {
		System.out.println(employeeDto.getEmpNum());
		try {
			Long empNum = employeeSerivce.join(employeeDto, file);
			return new ResponseEntity<String>("직원번호는 " + "(" + empNum + ")" + "입니다.", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("등록 실패", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/employeeDelete")
	public ResponseEntity<String> employeeDelete(@RequestParam("empNum") Long empNum) {
		try {
			employeeSerivce.delete(empNum);
			return new ResponseEntity<String>("삭제완료", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("삭제 실패", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/employeeList")
	public ResponseEntity<Map<String, Object>> employeeList(
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "jobName", required = false) String jobName,
			@RequestParam(name = "type", required = false) String type,
			@RequestParam(name = "word", required = false) String word) {
		System.out.println(jobName);
		Map<String, Object> res = new HashMap<>();
		System.out.println(page);
		try {
			PageInfo pageInfo = PageInfo.builder().curPage(page).build();
			List<EmployeeDto> employeeList = employeeSerivce.employeeListByPage(jobName, pageInfo, type, word);
			res.put("employeeList", employeeList);
			res.put("pageInfo", pageInfo);
			return new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/employeeDetail")
	public ResponseEntity<EmployeeDto> employeeDetail(@RequestBody Map<String, Long> param) {
		try {
			Long empNum = param.get("empNum");
			EmployeeDto empDto = employeeSerivce.detail(empNum);
			System.out.println(empDto);
			return new ResponseEntity<EmployeeDto>(empDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<EmployeeDto>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/empName")
	public String empName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("얘 이름이 " + authentication.getName());
		return authentication.getName();
	}

	@GetMapping("/profile/{num}")
	public void profileView(@PathVariable Long num, HttpServletResponse response) {
		try {
			FileInputStream fis = new FileInputStream(uploadProfile + num);
			OutputStream out = response.getOutputStream();
			FileCopyUtils.copy(fis, out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping("/file/{num}")
	public void fileView(@PathVariable Long num, HttpServletResponse response) {
		try {
			FileInputStream fis = new FileInputStream(uploadFile + num);
			OutputStream out = response.getOutputStream();
			FileCopyUtils.copy(fis, out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostMapping("/employeeModify")
	public ResponseEntity<String> EmployeeModify(@ModelAttribute EmployeeDto employeeDto,
			@RequestParam(name = "file", required = false) MultipartFile file) {
		try {
			System.out.println("controllerEmployee: " + employeeDto);
			employeeSerivce.modify(employeeDto, file);
			return new ResponseEntity<String>("수정성공", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("수정실패", HttpStatus.BAD_REQUEST);
		}
	}

}
