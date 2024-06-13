package com.kosta.care.service;

import java.util.List;
import java.util.Optional;

import com.kosta.care.dto.PatientDto;
import com.kosta.care.entity.Patient;

public interface PatientService {
	
	// 환자등록
	Patient joinPatient(PatientDto patientDto) throws Exception;
	
	// 환자 조회
	Optional<Patient> getPatientById(Long patNum) throws Exception;
	
	// 환자 리스트 보기
	List<Patient> getAllPatientSearch() throws Exception;
}
