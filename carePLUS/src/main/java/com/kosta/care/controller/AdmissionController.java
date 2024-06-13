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
	public ResponseEntity<List<Map<String, Object>>> admPatientList(@RequestParam("nurNum") Long nurNum) {
		System.out.println("리스트 가져오기 준비");
		try {
			System.out.println("리스트 가져오기 시작");
			List<Map<String, Object>> admission = admService.admPatientList(nurNum);
			System.out.println(admission.toString());
			return new ResponseEntity<List<Map<String, Object>>>(admission, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/nurPatientInfo")
	public ResponseEntity<List<Map<String, Object>>> admPatientDoctorRecordList(@RequestParam("admissionNum") Long admissionNum) {
		System.out.println("doctor record 리스트 가져오기 준비");
		try {
			System.out.println("리스트 가져오기 시작");
			List<Map<String, Object>> doctorRecord = admService.admPatientDoctorRecordList(admissionNum);
			System.out.println("결과:"+doctorRecord.toString());
			List<Map<String, Object>> nurseRecord = admService.admPatientNurseRecordList(admissionNum);
			System.out.println("결과:"+nurseRecord.toString());
			return new ResponseEntity<List<Map<String, Object>>>(doctorRecord, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/admissionDischarge")
	public ResponseEntity<Boolean> updateAdmissionDischarge(@RequestBody Map<String, Object> param) {
		try {
			Long admissionNum = Long.parseLong(param.get("admissionNum").toString());
			System.out.println(admissionNum);
            String admissionDischargeOpinion = param.get("admissionDischargeOpinion").toString();
            String admissionDischargeDateStr = param.get("admissionDischargeDate").toString();
            
         // Convert string to date
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date admissionDischargeDate = formatter.parse(admissionDischargeDateStr);
            java.sql.Date dd = new java.sql.Date(admissionDischargeDate.getTime());
            
			Boolean updateDischarge = admService.updateAdmissionDischarge(admissionNum, admissionDischargeOpinion,dd);
			System.out.println(updateDischarge);
			return new ResponseEntity<Boolean>(updateDischarge, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
		}
	}
}
