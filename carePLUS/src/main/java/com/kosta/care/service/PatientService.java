package com.kosta.care.service;

import java.util.Optional;

import com.kosta.care.entity.Patient;

public interface PatientService {
	
	// 환자 조회
	Optional<Patient> getPatientById(Long patNum) throws Exception;
}
