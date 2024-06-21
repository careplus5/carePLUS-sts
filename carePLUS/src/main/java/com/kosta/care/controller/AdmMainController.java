package com.kosta.care.controller;

import java.sql.Date;
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

import com.kosta.care.dto.DiagnosisDueDto;
import com.kosta.care.dto.TestRequestDto;
import com.kosta.care.entity.Admission;
import com.kosta.care.entity.Department;
import com.kosta.care.entity.DiagnosisDue;
import com.kosta.care.entity.DocDiagnosis;
import com.kosta.care.service.AdmService;
import com.kosta.care.service.DepartmentService;
import com.kosta.care.service.DiagnosisDueService;
import com.kosta.care.service.DoctorService;
import com.kosta.care.service.TestRequestService;

@RestController
public class AdmMainController {

	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private TestRequestService testRequestService;
	
	@Autowired
	private DiagnosisDueService diagnosisDueService;
	
	@Autowired
	private AdmService admService;
	
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
	
	@GetMapping("/doctorDueList")
	public ResponseEntity<List<List<DiagnosisDueDto>>> doctorDueList(@RequestParam("departmentNum") Long departmentNum,
			@RequestParam("diagnosisDueDate") String date) {
		try {
			List<List<DiagnosisDueDto>> doctorDueList = diagnosisDueService.doctorDiagnosisDueList(departmentNum, Date.valueOf(date));
			return new ResponseEntity<List<List<DiagnosisDueDto>>>(doctorDueList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<List<DiagnosisDueDto>>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/diagSearchAll")
	public ResponseEntity<List<DiagnosisDue>> diagSearchAll() {
		try {
			List<DiagnosisDue> diagnosisList = diagnosisDueService.diagSearchAll();
			return new ResponseEntity<List<DiagnosisDue>>(diagnosisList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<DiagnosisDue>>( HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/patDiagCheckList")
	public ResponseEntity<List<Map<String, Object>>> patDiagCheckList(@RequestBody Map<String, Long> param) {
		try {
			List<Map<String, Object>> docDiagnosisList = admService.patDiagCheckListByPatNum(param.get("patNum"));
			return new ResponseEntity<List<Map<String, Object>>>(docDiagnosisList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Map<String, Object>>>( HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/confirmAdmission")
	public ResponseEntity<List<Admission>> confirmAdmission(@RequestBody Map<String, Long> param) {
		try {
			List<Admission> admissionList = admService.getConfirmAdmission(param.get("patNum"));
			return new ResponseEntity<List<Admission>>(admissionList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Admission>>( HttpStatus.BAD_REQUEST);
		}
		
	}
}
