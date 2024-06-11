package com.kosta.care.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosta.care.entity.Admission;
import com.kosta.care.repository.AdmissionRepository;
import com.kosta.care.repository.DiagnosisDueRepository;
import com.kosta.care.repository.DiagnosisRepository;
import com.kosta.care.repository.DoctorRepository;
import com.kosta.care.repository.FavoriteMedicinesRepository;
import com.kosta.care.repository.MedicineRepository;
import com.kosta.care.repository.NurseRepository;
import com.kosta.care.repository.PatientRepository;
import com.querydsl.core.Tuple;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdmissionServiceImpl implements AdmissionService {


	private final DiagnosisDueRepository diagnosisDueRepository;
	private final NurseRepository nurRepository;
	private final AdmissionRepository admRepository;
	private final PatientRepository patientRepository;
	private final DoctorRepository doctorRepository;
	private final DiagnosisRepository diagnosisRepository;
	private final FavoriteMedicinesRepository favoriteMedicinesRepository;
	private final MedicineRepository medicineRepository;
	
	private final ObjectMapper objectMapper;
	
	
	@Override
	public List<Map<String, Object>> admPatientList(Long nurNum) {
		// admission내역
		List<Tuple> tuples = admRepository.findAdmPatientByNurNum(nurNum);
		System.out.println(nurNum);
		List<Map<String, Object>> admList = new ArrayList<>();
		
		for(Tuple tuple : tuples) {
			
			Admission admission = tuple.get(0,Admission.class);
			String docDepartment = tuple.get(1, String.class);
			String docName = tuple.get(2,String.class);
			System.out.println("부서는"+docDepartment);
			System.out.println("이름은"+docName);
	Map<String, Object> map = objectMapper.convertValue(tuple, Map.class);
			map.put("admission", admission);
		map.put("docDepartment", docDepartment);
			map.put("docName", docName);
			admList.add(map);
		}
		
		return admList;
	}

}
