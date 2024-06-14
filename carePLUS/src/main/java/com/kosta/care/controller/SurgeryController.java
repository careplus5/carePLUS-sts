package com.kosta.care.controller;

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

import com.kosta.care.dto.SurRecordDto;
import com.kosta.care.service.SurgeryService;

@RestController
public class SurgeryController {
	
	@Autowired
	private SurgeryService surgeryService;
	
	@GetMapping("/surPatientList")
	public ResponseEntity<List<Map<String, Object>>> surPatientList(@RequestParam("docNum") Long docNum) {
		try {
			List<Map<String, Object>> surgeryList = surgeryService.surgeryListByDocNum(docNum);
			return new ResponseEntity<List<Map<String,Object>>>(surgeryList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Map<String,Object>>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/surPatientInfo")
	public ResponseEntity<Map<String, Object>> surPatientInfo(@RequestParam("surgeryNum") Long surgeryNum) {
		try {
			Map<String, Object> surInfo = surgeryService.surgeryInfoBySurgeryNum(surgeryNum);
			return new ResponseEntity<Map<String,Object>>(surInfo, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/surNurseList")
	public ResponseEntity<List<Map<String, Object>>> surNurseList(@RequestParam("surgeryNum") Long surgeryNum) {
		try {
			List<Map<String, Object>> surNurseList = surgeryService.surNurseListBySurgeryNum(surgeryNum);
			return new ResponseEntity<List<Map<String, Object>>>(surNurseList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/surRecordSubmit")
	public ResponseEntity<Boolean> surRecordSubmit(@RequestBody SurRecordDto surRecordDto) {
		try {
			Boolean isSuccess = surgeryService.submitSurRecord(surRecordDto);
			return new ResponseEntity<Boolean>(isSuccess, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
		}
	}

}
