package com.kosta.care.controller;

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
