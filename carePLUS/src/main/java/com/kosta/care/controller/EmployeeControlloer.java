package com.kosta.care.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.care.dto.EmployeeDto;
import com.kosta.care.service.EmployeeSerivce;

@RestController
public class EmployeeControlloer {
	
	@Autowired
	private EmployeeSerivce employeeSerivce;
	
	@PostMapping("/employeeAdd")
	public ResponseEntity<String> EmployeeAdd(@ModelAttribute EmployeeDto employeeDto, @RequestParam(name="file", required = false) MultipartFile file){
		System.out.println(employeeDto.getEmpNum());
		try {
			System.out.println(employeeDto);
			employeeSerivce.Join(employeeDto,file);
			return new ResponseEntity<String>("등록 완료", HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("등록 실패", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/employeeDelete")
	public ResponseEntity<String> EmployeeDelete(@RequestParam("empNum") Long empNum){
		try {
			employeeSerivce.Delete(empNum);
			return new ResponseEntity<String>("삭제완료", HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("삭제 실패", HttpStatus.BAD_REQUEST);
		}
	}
	
//	@GetMapping("/employeeList")
//	public ResponseEntity<Map<String,Object>>EmployeeList(@RequestParam(name="page", required = false, defaultValue = "1") Integer page,
//			@RequestParam(name="type", required = false) String type,
//			@RequestParam(name="word", required = false) String word){
//		Map<String, Object> res = new HashMap<>();
//		try {
//			
//			return new ResponseEntity<Map<String,Object>>(res, HttpStatus.OK);
//		}catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
//		}
//	}
	
	@PostMapping("/employeeDetail")
	public ResponseEntity<EmployeeDto>EmployeeDetail(@RequestParam("empNum") Long empNum){
		try {
			EmployeeDto empDto = employeeSerivce.Detail(empNum);
			return new ResponseEntity<EmployeeDto>(empDto, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<EmployeeDto>(HttpStatus.BAD_REQUEST);
		}
	}
	
//	@PostMapping("/employeeModify")
//	public ResponseEntity<String>EmployeeModify(@RequestParam(name="file", required = false)MultipartFile file,
//			@RequestParam("jobNum")Long jobNum, @RequestParam("departmentNum")Long departmentNum,
//			@RequestParam(name="department2Name", required = false, defaultValue = "null")String department2Name, @RequestParam("empPosition")String empPosition,
//			@RequestParam("empName")String empName, @RequestParam("empTel")String empTel, @RequestParam("empEmail")String empEmail,
//			@RequestParam("empNum")Long empNum, @RequestParam("empPassword")String empPassword
//			){
//		try {
//			
//		}catch (Exception e) {
//			e.printStackTrace()
//			return new ResponseEntity<String>("삭제실패", HttpStatus.BAD_REQUEST)
//		}
//	}
	
}
