package com.kosta.care.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.care.entity.NurDiagnosis;
import com.kosta.care.repository.NurseRepository;
import com.kosta.care.service.NurseDiagnosisService;

@RestController
public class NurseDiagnosisController {
	@Autowired
	private NurseRepository nurseRepository;
	
	@Autowired
	private NurseDiagnosisService diagService;
	
	
	@PostMapping("/nurDiagPatientRecord")
	public ResponseEntity<Boolean> nurDiagPatientRecord(@RequestBody Map<String, Object> param){
		try {
			 
            Integer nurDiagNum = (Integer)param.get("nurDiagNum");
            System.out.println("nurDiagNum: "+nurDiagNum);
            String nurDiagContent = (String) param.get("nurDiagContent");
            System.out.println("content: "+nurDiagContent);
            String nurDiagnosisDateStr = param.get("nurDiagnosisDate").toString();
            System.out.println("date: "+nurDiagnosisDateStr);
            
         // Convert string to date
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date nurDiagnosisDate1 = formatter.parse(nurDiagnosisDateStr);
            java.sql.Date dd = new java.sql.Date(nurDiagnosisDate1.getDate());
            
            NurDiagnosis diagnosis = diagService.updateNurDiagnosis(Long.parseLong(String.valueOf(nurDiagNum)), nurDiagContent, dd);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(true);
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
		}
	}

	// 진료 환자 리스트 전체 가져오기 
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
