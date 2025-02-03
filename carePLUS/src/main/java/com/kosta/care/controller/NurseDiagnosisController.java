package com.kosta.care.controller;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.kosta.care.config.auth.AuthEmployee;
import com.kosta.care.config.auth.AuthException;
import com.kosta.care.dto.EmployeeAuthDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.care.entity.NurDiagnosis;
import com.kosta.care.repository.NurseRepository;
import com.kosta.care.service.NurseDiagnosisService;

@PreAuthorize("hasRole('ROLE_NURSE')")
@RestController
public class NurseDiagnosisController {

	@Autowired
	private NurseDiagnosisService diagService;
	
	
	@PostMapping("/nurDiagPatientRecord")
	public ResponseEntity<Boolean> nurDiagPatientRecord(@RequestBody EmployeeAuthDto employee, @RequestBody Map<String, Object> param){
		AuthException.invalidNurseAccess(employee.getIdentity());
		try {
            Integer nurDiagNum = (Integer)param.get("nurDiagNum");
            System.out.println("nurDiagNum: "+nurDiagNum);
            String nurDiagContent = (String) param.get("nurDiagContent");
            System.out.println("content: "+nurDiagContent);
            String nurDiagnosisDateStr = param.get("nurDiagnosisDate").toString();
            System.out.println("date: "+nurDiagnosisDateStr);
            

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


	@GetMapping("/nurDiagPatientList")
	public ResponseEntity<List<Map<String, Object>>> nurDiagPatientList(@AuthEmployee EmployeeAuthDto employee) {
		AuthException.invalidNurseAccess(employee.getIdentity());
		try {
			List<Map<String, Object>> diagnosis = diagService.diagPatientList(employee.getId());
			return new ResponseEntity<List<Map<String, Object>>>(diagnosis, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.BAD_REQUEST);
		}
	}

}
