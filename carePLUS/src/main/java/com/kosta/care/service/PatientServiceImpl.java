package com.kosta.care.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.care.dto.PatientDto;
import com.kosta.care.entity.Patient;
import com.kosta.care.repository.PatientRepository;

@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientRepository patientRepository;

	// 환자 번호로 환자 조회
	@Override
	public Optional<Patient> getPatientById(Long patNum) throws Exception {
		
		return patientRepository.findById(patNum);
		
	}

	// 모든 환자 리스트 조회
	@Override
	public List<Patient> getAllPatientSearch() throws Exception {
		return patientRepository.findAll();
	}

	@Override
	public Patient joinPatient(PatientDto patientDto) throws Exception {
		
		return patientRepository.save(patientDto.patientEntity());
	}
	
	
}
