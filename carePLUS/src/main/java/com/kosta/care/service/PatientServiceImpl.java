package com.kosta.care.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.care.entity.Patient;
import com.kosta.care.repository.PatientRepository;

@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientRepository patientRepository;

	@Override
	public Optional<Patient> getPatientById(Long patNum) throws Exception {
		
		return patientRepository.findById(patNum);
		
	}
	
}
