package com.kosta.care.service;

import java.util.List;
import java.util.Optional;

import com.kosta.care.dto.PatientDto;
import com.kosta.care.entity.Patient;
import com.kosta.care.util.PageInfo;

public interface PatientService {
	
//	// 환자등록
//	Patient joinPatient(PatientDto patientDto) throws Exception;
	
	// 환자 조회
	Optional<Patient> getPatientById(Long patNum) throws Exception;
	
	// 환자 리스트 보기 (전체)
//	List<PatientDto> getAllPatientSearch() throws Exception;
	
	// 환자 리스트 보기
	List<PatientDto> getAllPatientSearch(String word, String type) throws Exception;
	
	// 환자 번호로 입원 진료 기록 모두 조회
	PatientDto getPatientStorage(Long patNum) throws Exception;
}
