package com.kosta.care.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosta.care.entity.NurDiagnosis;
import com.kosta.care.repository.NurDiagnosisRepository;
import com.querydsl.core.Tuple;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NurseDiagnosisServiceImpl implements NurseDiagnosisService {

	private final NurDiagnosisRepository diagRepository;
	private final ObjectMapper objectMapper;
	
	@Override
	public List<Map<String, Object>> diagPatientList(Long nurNum) {

		List<Tuple> tuples = diagRepository.findDiagPatientByNurNum(nurNum);
		System.out.println(tuples.toString());
		List<Map<String, Object>> diagList = new ArrayList<>();
		
		for(Tuple tuple : tuples) {
			System.out.println(nurNum+"찾아보자이");
			NurDiagnosis diagnosis = tuple.get(0, NurDiagnosis.class);
			String patName = tuple.get(1, String.class);
			String departmentName = tuple.get(2,String.class);
			String docName = tuple.get(3, String.class);
			System.out.println("부서는"+departmentName);
			System.out.println("이름은"+docName);
			  Map<String, Object> map = new HashMap<>();
			map.put("diagnosis", diagnosis);
		map.put("patName", patName);
			map.put("docName", docName);
			map.put("departmentName", departmentName);
			diagList.add(map);
		}
		
		return diagList;
	}

}
