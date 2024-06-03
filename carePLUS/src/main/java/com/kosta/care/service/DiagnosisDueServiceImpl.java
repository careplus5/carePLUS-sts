package com.kosta.care.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosta.care.entity.DiagnosisDue;
import com.kosta.care.entity.Disease;
import com.kosta.care.entity.Doctor;
import com.kosta.care.repository.DiagnosisDueRepository;
import com.kosta.care.repository.DiagnosisRepository;
import com.kosta.care.repository.DoctorRepository;
import com.kosta.care.repository.PatientRepository;
import com.querydsl.core.Tuple;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiagnosisDueServiceImpl implements DiagnosisDueService {
	
	private final DiagnosisDueRepository diagnosisDueRepository;
	private final PatientRepository patientRepository;
	private final DoctorRepository doctorRepository;
	private final DiagnosisRepository diagnosisRepository;
	
	private final ObjectMapper objectMapper;
	
	@Override
	public List<Map<String, Object>> diagDueListByDocNum(Long docNum) {
		List<Tuple> tuples = diagnosisRepository.findDiagDueListByDocNumAndDiagDueDate(docNum);
		List<Map<String, Object>> diagDueList = new ArrayList<>();
		
		for(Tuple tuple : tuples) {
			DiagnosisDue diagDue = tuple.get(0, DiagnosisDue.class);
			String patName = tuple.get(1, String.class);
			Long docDiagNum = tuple.get(2, Long.class);
			String docDiagState = tuple.get(3, String.class);

			Map<String, Object> map = objectMapper.convertValue(diagDue, Map.class);
			map.put("patName", patName);
			map.put("docDiagNum", docDiagNum);
			map.put("docDiagState", docDiagState);
			diagDueList.add(map);
		}
		
		if(diagDueList.isEmpty()) {
			return null;
		}
		return diagDueList;
	}

	@Override
	public Map<String, Object> diagDueInfoByDocDiagNum(Long docDiagNum) {
		Tuple tuple = diagnosisRepository.findDiagDueInfoByDocDiagNum(docDiagNum);
		
		DiagnosisDue diagDue = tuple.get(0, DiagnosisDue.class);
		String patName = tuple.get(1, String.class);
		String patJumin = tuple.get(2, String.class);
		String docDiagState = tuple.get(3, String.class);

		Map<String, Object> map = objectMapper.convertValue(diagDue, Map.class);
		map.put("patName", patName);
		map.put("patJumin", patJumin);
		map.put("docDiagState", docDiagState);
		
		return map;
	}

	@Override
	public void updateDocDiagnosisState(Long docDiagNum, String newState) {
		diagnosisRepository.modifyDocDiagnosisState(docDiagNum, newState);
	}

	@Override
	public List<Map<String, Object>> diseaseListByDeptNum(Long docNum) {
		Optional<Doctor> odoctor = doctorRepository.findById(docNum);
		List<Tuple> tuples = diagnosisRepository.findDiseaseListByDeptNum(odoctor.get().getDepartmentId());
		List<Map<String, Object>> diseaseList = new ArrayList<>();
		
		for(Tuple tuple : tuples) {
			Disease disease = tuple.get(0, Disease.class);
			String deptName = tuple.get(1, String.class);

			Map<String, Object> map = objectMapper.convertValue(disease, Map.class);
			map.put("deptName", deptName);
			diseaseList.add(map);
		}
		
		if(diseaseList.isEmpty()) {
			return null;
		}
		return diseaseList;
	}

}
