package com.kosta.care.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.care.dto.PatientDto;
import com.kosta.care.entity.Patient;
import com.kosta.care.service.PatientService;
import com.kosta.care.util.PageInfo;

@RestController
public class PatientController {
	
	@Autowired
	private PatientService patientService;
	
	// 환자 등록
	@PostMapping("/patientJoin")
	public ResponseEntity<Patient> patientJoin(PatientDto patientDto) {
		try {
			Patient patient = patientService.joinPatient(patientDto);
			return new ResponseEntity<Patient>(patient, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Patient>(HttpStatus.BAD_REQUEST);
			
		}
	}
	
	// 환자 조회
	@PostMapping("/patientSearch")
	public ResponseEntity<List<PatientDto>> allPatient(@RequestBody Map<String,String> param) {
		String type = param.get("type");
		String keyword = param.get("keyword");
		try {
			List<PatientDto> patientDtoList = patientService.getAllPatientSearch(type, keyword);
			
			
			if(!patientDtoList.isEmpty()) {
				return new ResponseEntity<List<PatientDto>>(patientDtoList, HttpStatus.OK);
			} else {
				return new ResponseEntity<List<PatientDto>>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<List<PatientDto>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	// 접수등록에서 환자 조회
	@PostMapping("/patNumCheck")
	public ResponseEntity<Patient> patNumCheck(@RequestBody Map<String,Long> param)  {
		System.out.println(param);
		try {
            Optional<Patient> oPatient = patientService.getPatientById(param.get("patNum"));

            if (oPatient.isPresent()) {
                return new ResponseEntity<Patient>(oPatient.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<Patient>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Patient>(HttpStatus.BAD_REQUEST);
        }
	}
}
