package com.kosta.care.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.care.dto.DiagnosisDueDto;
import com.kosta.care.entity.Disease;
import com.kosta.care.entity.Doctor;
import com.kosta.care.repository.DoctorRepository;
import com.kosta.care.service.DiagnosisDueService;

@RestController
public class DocDiagnosisController {
	
	@Autowired
	private DiagnosisDueService diagnosisDueService;
	
	@Autowired 
	private DoctorRepository doctorRepository;
	
	@GetMapping("/diagPatientList")
	public ResponseEntity<List<Map<String, Object>>> diagPatientList(@RequestParam("docNum") Long docNum) {
		try {
			List<Map<String, Object>> diagDueList = diagnosisDueService.diagDueListByDocNum(docNum);
			return new ResponseEntity<List<Map<String, Object>>>(diagDueList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/diagPatientInfo")
	public ResponseEntity<Map<String, Object>> diagPatientInfo(@RequestParam("docDiagNum") Long docDiagNum) {
		String newState = "진료중";
		try {
			diagnosisDueService.updateDocDiagnosisState(docDiagNum, newState);
			Map<String, Object> patInfo = diagnosisDueService.diagDueInfoByDocDiagNum(docDiagNum);
			return new ResponseEntity<Map<String,Object>>(patInfo, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/diseaseList")
	public ResponseEntity<List<Map<String, Object>>> diseaseList(@RequestParam("docNum") Long docNum) {
		try {
			List<Map<String, Object>> diseaseList = diagnosisDueService.diseaseListByDeptNum(docNum);
			return new ResponseEntity<List<Map<String, Object>>>(diseaseList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
