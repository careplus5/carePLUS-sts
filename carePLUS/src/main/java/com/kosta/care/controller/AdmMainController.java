package com.kosta.care.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.care.dto.TestRequestDto;
import com.kosta.care.entity.Department;
import com.kosta.care.entity.TestRequest;
import com.kosta.care.service.DepartmentService;
import com.kosta.care.service.TestRequestService;

@RestController
public class AdmMainController {

	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private TestRequestService testRequestService;
	
	// 진료 부서 조회 (리액트에서 select option에 쓰임)
	@GetMapping("/departments")
	public ResponseEntity<List<Department>> departments() {
		
		try {
			List<Department> departmentList = departmentService.departmentList();
			System.out.println(departmentList);
			return new ResponseEntity<List<Department>> (departmentList ,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Department>> (HttpStatus.BAD_REQUEST);
		}
	}
	
	// 환자 조회 검사 (검사예약)
	@PostMapping("/testRequestPatientLatest")
	public ResponseEntity<TestRequestDto> getLatestTestRequestByPatient(@RequestBody Map<String,Long> param) {
		System.out.println(param);
		try {
			TestRequestDto testRequestdto = testRequestService.getLatestTestRequestByPatNum(param.get("patNum"));
			if (testRequestdto != null) {
				return new ResponseEntity<TestRequestDto> (testRequestdto,HttpStatus.OK);
			} else {
				return new ResponseEntity<TestRequestDto> (HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<TestRequestDto> (HttpStatus.BAD_REQUEST);
		}
	}
}
