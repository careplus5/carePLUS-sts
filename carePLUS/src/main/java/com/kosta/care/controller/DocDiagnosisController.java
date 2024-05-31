package com.kosta.care.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.care.dto.DiagnosisDueDto;
import com.kosta.care.service.DiagnosisDueService;

@RestController
public class DocDiagnosisController {
	
	@Autowired
	private DiagnosisDueService diagnosisDueService;
	
	@GetMapping("/diagPatientList")
	public ResponseEntity<List<DiagnosisDueDto>> diagPatientList(@RequestParam("docNum") Long docNum) {
		try {
			List<DiagnosisDueDto> diagDueList = diagnosisDueService.diagDueListByDocNum(docNum);
			return new ResponseEntity<List<DiagnosisDueDto>>(diagDueList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<DiagnosisDueDto>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
