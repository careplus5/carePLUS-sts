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

import com.kosta.care.dto.DocDiagnosisDto;
import com.kosta.care.dto.PatientDto;
import com.kosta.care.entity.Medicine;
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
		String newState = "ing";
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
	
	@GetMapping("/medicineList")
	public ResponseEntity<List<Medicine>> medicineList(@RequestParam(name="medSearchType", required = false) String medSearchType, 
													@RequestParam(name="medSearchKeyword", required = false) String medSearchKeyword) {
		try {
			List<Medicine> medicineList = diagnosisDueService.medicineList(medSearchType, medSearchKeyword);
			return new ResponseEntity<List<Medicine>>(medicineList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Medicine>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/favMedicineList")
	public ResponseEntity<List<Map<String, Object>>> favMedicineList(@RequestParam("docNum") Long docNum) {
		try {
			List<Map<String, Object>> favMedicineList = diagnosisDueService.favMedicineList(docNum);
			return new ResponseEntity<List<Map<String,Object>>>(favMedicineList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Map<String,Object>>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/addFavMedicine")
	public ResponseEntity<Boolean> addFavMedicine(@RequestBody Map<String,Object> param) {
		String username = (String) param.get("docNum");
		Long docNum = Long.parseLong(username);

		String medicineNum = (String) param.get("medicineNum");
		
		try {
			Boolean isAddedFavMed = diagnosisDueService.addFavMedicine(docNum, medicineNum);
			return new ResponseEntity<Boolean>(isAddedFavMed, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/diagnosisSubmit")
	public ResponseEntity<Boolean> diagnosisSubmit(@RequestBody DocDiagnosisDto docDiagDto) {
		try {
			Boolean isSuccess = diagnosisDueService.submitDiagnosis(docDiagDto);
			return new ResponseEntity<Boolean>(isSuccess, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/docDiagPatList")
	public ResponseEntity<List<Map<String, Object>>> docDiagPatList(@RequestParam("docNum") Long docNum,
										@RequestParam(name="searchType", required = false) String searchType, 
										@RequestParam(name="searchKeyword", required = false) String searchKeyword) {
		try {
			List<Map<String, Object>> docPatList = diagnosisDueService.docPatListByDocNum(docNum, searchType, searchKeyword);
			return new ResponseEntity<List<Map<String,Object>>>(docPatList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Map<String,Object>>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/prevDiagRecord")
	public ResponseEntity<List<Map<String, Object>>> prevDiagRecord(@RequestParam("patNum") Long patNum,
										@RequestParam(name="searchType", required = false) String searchType,
										@RequestParam(name="searchKeyword", required = false) String searchKeyword) {
		try {
			List<Map<String, Object>> patDiagList = diagnosisDueService.patDiagListByPatNum(patNum, searchType, searchKeyword);
			return new ResponseEntity<List<Map<String,Object>>>(patDiagList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Map<String,Object>>>(HttpStatus.BAD_REQUEST);
		}
	}
	
}
