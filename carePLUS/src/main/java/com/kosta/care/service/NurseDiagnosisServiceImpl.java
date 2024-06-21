package com.kosta.care.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosta.care.entity.DocDiagnosis;
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
			DocDiagnosis docDiagnosis = tuple.get(4, DocDiagnosis.class);
			String patJumin = tuple.get(5, String.class).split("-")[0];
			System.out.println("부서는"+departmentName);
			System.out.println("이름은"+docName);
			  Map<String, Object> map = new HashMap<>();
			map.put("diagnosis", diagnosis);
		map.put("patName", patName);
			map.put("docName", docName);
			map.put("departmentName", departmentName);
			map.put("DocDiagnosis", docDiagnosis);
			map.put("patJumin",patJumin);
			diagList.add(map);
		}
		
		return diagList;
	}

	@Override
	public NurDiagnosis updateNurDiagnosis(Long nurDiagNum, String nurDiagContent, Date nurDiagnosisDate) {
		NurDiagnosis diagnosis = diagRepository.findByNurDiagNum(nurDiagNum);
		
		if(diagnosis!=null) {
			diagnosis.setNurDiagnosisDate(nurDiagnosisDate);
			System.out.println("date:"+nurDiagnosisDate);
			diagnosis.setNurDiagContent(nurDiagContent);
			 System.out.println("content: "+nurDiagContent);
			 diagRepository.save(diagnosis); // 업데이트 내용을 영속성 컨텍스트에 반영
			
			return diagnosis;
		} else {
		System.out.println("진료 기록 등록 실패");
			return null;
		}
		
	}

}
