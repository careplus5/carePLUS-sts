package com.kosta.care.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.care.repository.NurseRepository;
import com.kosta.care.service.NurseDiagnosisService;

@RestController
public class NurseDiagnosisController {
	@Autowired
	private NurseRepository nurseRepository;
	
	@Autowired
	private NurseDiagnosisService diagService;
	

	
	@GetMapping("/nurDiagPatientList")
	public ResponseEntity<List<Map<String, Object>>> nurDiagPatientList(@RequestParam("nurNum") Long nurNum) {
		System.out.println("diag 리스트 가져오기 준비");
		try {
			System.out.println("diag 리스트 가져오기 시작");
			System.out.println(nurNum);
			List<Map<String, Object>> diagnosis = diagService.diagPatientList(nurNum);
			System.out.println(diagnosis.toString());
			return new ResponseEntity<List<Map<String, Object>>>(diagnosis, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.BAD_REQUEST);
		}
	}

}
