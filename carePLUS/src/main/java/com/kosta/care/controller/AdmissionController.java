package com.kosta.care.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.care.repository.NurseRepository;
import com.kosta.care.service.AdmissionService;

@RestController
public class AdmissionController {
	
//	@Autowired
//	private DiagnosisDueService diagnosisDueService;
//	
	@Autowired
	private AdmissionService admService;
	@Autowired 
	private NurseRepository nurRepository;
	
	@GetMapping("/wardPatientList")
	public ResponseEntity<List<Map<String, Object>>> admPatientList(@RequestParam("username") Long nurNum) {
		System.out.println("리스트 가져오기 준비");
		try {
			System.out.println("리스트 가져오기 시작");
			List<Map<String, Object>> admission = admService.admPatientList(nurNum);
			return new ResponseEntity<List<Map<String, Object>>>(admission, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
