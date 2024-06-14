package com.kosta.care.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosta.care.entity.Admission;
import com.kosta.care.entity.AdmissionRecord;
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
		System.out.println(tuples.toString());
		List<Map<String, Object>> admList = new ArrayList<>();
		
		for(Tuple tuple : tuples) {
			System.out.println(nurNum+"찾아보자이");
			Admission admission = tuple.get(0,Admission.class);
			String docDepartment = tuple.get(1, String.class);
			String docName = tuple.get(2,String.class);
			String patName = tuple.get(3,String.class);
			System.out.println("부서는"+docDepartment);
			System.out.println("이름은"+docName);
			  Map<String, Object> map = new HashMap<>();
			map.put("admission", admission);
		map.put("docDepartment", docDepartment);
			map.put("docName", docName);
			map.put("patName", patName);
			admList.add(map);
		}
		
		return admList;
	}
	// 의사 입원 기록
	@Override
	public List<Map<String, Object>> admPatientDoctorRecordList(Long admissionNum) {
		// admission내역
		System.out.println(admissionNum+"찾아보자이");
		
		List<Tuple> tuples = admRepository.findAdmPatientDoctorRecordByAdmissionNum(admissionNum);
		System.out.println(tuples.toString());
		List<Map<String, Object>> recordList = new ArrayList<>();
		
		for(Tuple tuple : tuples) {
			
			AdmissionRecord record = tuple.get(0,AdmissionRecord.class);
			String docName = tuple.get(1,String.class);
			System.out.println("이름은"+docName);
			  Map<String, Object> map = new HashMap<>();
			map.put("record", record);
			map.put("docName", docName);
			recordList.add(map);
		}
		
		return recordList;
	}
	
	//간호사 입원 기록
	@Override
	public List<Map<String, Object>> admPatientNurseRecordList(Long admissionNum) {
		// admission내역
		System.out.println(admissionNum+"또 찾아보자이");
		
		List<Tuple> tuples = admRepository.findAdmPatientNurseRecordByAdmissionNum(admissionNum);
		System.out.println(tuples.toString());
		List<Map<String, Object>> recordList = new ArrayList<>();
		
		for(Tuple tuple : tuples) {
			
			AdmissionRecord record = tuple.get(0,AdmissionRecord.class);
			String nurName = tuple.get(1,String.class);
			System.out.println("이름은"+nurName);
			  Map<String, Object> map = new HashMap<>();
			map.put("record", record);
			map.put("nurName", nurName);
			recordList.add(map);
		}
		
		return recordList;
	}


	
	@Override
	public Boolean updateAdmissionDischarge(Long admissionNum,String admissionDischargeOpinion, Date admissionDischargeDate) {
		try {
			Admission admission = admRepository.findByAdmissionNum(admissionNum);
			
			if("ing".equals(admission.getAdmissionStatus())) {
				admission.setAdmissionDischargeDate(admissionDischargeDate);
				admission.setAdmissionDischargeOpinion(admissionDischargeOpinion);
				admission.setAdmissionStatus("end");
				admRepository.save(admission);
				return true;
			}
			
			return false;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
}
