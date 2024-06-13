package com.kosta.care.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.care.dto.PatientDto;
import com.kosta.care.entity.Patient;
import com.kosta.care.service.PatientService;

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
	
	// 모든 환자 조회
	@PostMapping("/allPatient")
	public ResponseEntity<List<Patient>> allPatient() {
		
		try {
			List<Patient> patientList = patientService.getAllPatientSearch();
			
			if(!patientList.isEmpty()) {
				return new ResponseEntity<List<Patient>>(patientList, HttpStatus.OK);
			} else {
				return new ResponseEntity<List<Patient>>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<List<Patient>>(HttpStatus.BAD_REQUEST);
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
