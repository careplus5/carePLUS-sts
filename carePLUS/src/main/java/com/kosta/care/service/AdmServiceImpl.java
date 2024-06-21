package com.kosta.care.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosta.care.dto.DiagnosisDueDto;
import com.kosta.care.entity.Admission;
import com.kosta.care.entity.DiagnosisDue;
import com.kosta.care.entity.DocDiagnosis;
import com.kosta.care.entity.Patient;
import com.kosta.care.repository.AdmDslRepository;
import com.kosta.care.repository.AdmissionRepository;
import com.kosta.care.repository.DiagnosisDueRepository;
import com.kosta.care.repository.DocDiagnosisRepository;
import com.kosta.care.repository.PatientRepository;
import com.querydsl.core.Tuple;

@Service
public class AdmServiceImpl implements AdmService {
	
	@Autowired
	private DocDiagnosisRepository docDiagnosisRepository;
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private DiagnosisDueRepository diagnosisDueRepository;
	@Autowired
	private AdmissionRepository admissionRepository;
	@Autowired
	private AdmDslRepository admDslRepository;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public List<Map<String, Object>> patDiagCheckListByPatNum(Long patNum) {
		List<Tuple> tuples = admDslRepository.findPatDiagCheckListByPatNum(patNum);
		List<Map<String, Object>> patDiagCheckList = new ArrayList<>();
		
		for(Tuple tuple : tuples) {
			DocDiagnosis docDiagnosis = tuple.get(0, DocDiagnosis.class);
			String patName = tuple.get(1, String.class);
			String docName = tuple.get(2, String.class);
			String departmentName = tuple.get(3, String.class);
			String testName = tuple.get(4, String.class);
			
			Map<String, Object> map = objectMapper.convertValue(docDiagnosis, Map.class);
			map.put("patName", patName);
			map.put("docName", docName);
			map.put("deptName", departmentName);
			map.put("testName", testName);
			patDiagCheckList.add(map);
		}
		
		if(patDiagCheckList.isEmpty()) return null;
		
		return patDiagCheckList;
	}
	
	@Override
	public List<Admission> getConfirmAdmission(Long patNum) throws Exception {
		// TODO Auto-generated method stub
		return admissionRepository.findByPatNumOrderByAdmissionNumDesc(patNum);
	}

	// 진료예약 및 환자등록 및 의사테이블 저장
		@Override
		@Transactional
		public void diagnosisRegister(DiagnosisDueDto diagnosisDueDto) throws Exception {
		    // 환자 등록
			if(diagnosisDueDto.getPatNum()==null) {
				Patient patient = Patient.builder()
									.patName(diagnosisDueDto.getPatName())
									.patJumin(diagnosisDueDto.getPatJumin())
									.patGender(diagnosisDueDto.getPatGender())
									.patTel(diagnosisDueDto.getPatTel())
									.patHeight(diagnosisDueDto.getPatHeight())
									.patWeight(diagnosisDueDto.getPatWeight())
									.patAddress(diagnosisDueDto.getPatAddress())
									.patHistory(diagnosisDueDto.getPatHistory())
									.patBloodType(diagnosisDueDto.getPatBloodType())
									.build();
				patientRepository.save(patient);
				diagnosisDueDto.setPatNum(patient.getPatNum());
			}

		    // 진료예약 정보 설정
		    DiagnosisDue diagnosisDue = DiagnosisDue.builder()
		    		.patNum(diagnosisDueDto.getPatNum())
		    		.docNum(diagnosisDueDto.getDocNum())
		    		.diagnosisDueDate(diagnosisDueDto.getDiagnosisDueDate())
		    		.diagnosisDueTime(diagnosisDueDto.getDiagnosisDueTime())
		    		.diagnosisDueState(diagnosisDueDto.getDiagnosisDueState())
		    		.diagnosisDueEtc(diagnosisDueDto.getDiagnosisDueEtc())
		    		.build();

		    diagnosisDueRepository.save(diagnosisDue);

		    // DocDiagnosis 정보 설정
		    DocDiagnosis docDiagnosis = diagnosisDueDto.toDocDiagnosis();
		    docDiagnosis.setPatNum(diagnosisDueDto.getPatNum());
		    docDiagnosis.setDocNum(diagnosisDueDto.getDocNum());
		    docDiagnosis.setDocDiagnosisState("wait");  // 진료상태

		    docDiagnosisRepository.save(docDiagnosis);
		}

}
