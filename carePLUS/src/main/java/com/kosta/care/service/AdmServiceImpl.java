package com.kosta.care.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.kosta.care.entity.Medicine;
import com.kosta.care.entity.Patient;
import com.kosta.care.entity.Prescription;
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
		Map<Long, Date> firstDiagDateMap = new HashMap<>();

		for(Tuple tuple : tuples) {
			DocDiagnosis docDiagnosis = tuple.get(0, DocDiagnosis.class);
			String patName = tuple.get(1, String.class);
			String docName = tuple.get(2, String.class);
			String departmentName = tuple.get(3, String.class);
			String testName = tuple.get(4, String.class);
			
			Date thisDate = docDiagnosis.getDocDiagnosisDate();
			//map에 존재하는 날짜를 thisDate와 비교해 더 작은 값을 반환 (초기 진단 날짜)
			firstDiagDateMap.merge(patNum, thisDate, (existingDate, newDate) -> 
	            existingDate.before(newDate) ? existingDate : newDate);
			
			Map<String, Object> map = objectMapper.convertValue(docDiagnosis, Map.class);
			map.put("patName", patName);
			map.put("docName", docName);
			map.put("deptName", departmentName);
			map.put("testName", testName);
			patDiagCheckList.add(map);
		}
		for (Map<String, Object> map : patDiagCheckList) {
	        map.put("firstDiagDate", firstDiagDateMap.get(patNum));
	    }
		
		if(patDiagCheckList.isEmpty()) return null;
		
		return patDiagCheckList;
	}
	
	@Override
	public List<Map<String, Object>> patAdmCheckListByPatNum(Long patNum) {
		List<Tuple> tuples = admDslRepository.findPatAdmCheckListByPatNum(patNum);
		List<Map<String, Object>> patAdmCheckList = new ArrayList<>();

		for(Tuple tuple : tuples) {
			Admission admission = tuple.get(0, Admission.class);
			String patName = tuple.get(1, String.class);
			String docName = tuple.get(2, String.class);
			String departmentName = tuple.get(3, String.class);
			String nurName = tuple.get(4, String.class);
			
			Map<String, Object> map = objectMapper.convertValue(admission, Map.class);
			map.put("patName", patName);
			map.put("docName", docName);
			map.put("deptName", departmentName);
			map.put("nurName", nurName);
			patAdmCheckList.add(map);
		}
		
		if(patAdmCheckList.isEmpty()) return null;
		
		return patAdmCheckList;
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

		@Override
		public List<Map<String, Object>> getPrescriptionList(Long patNum) throws Exception {
			List<Tuple> tuples = admDslRepository.findPatPrescListByPatNum(patNum);
			List<Map<String, Object>> patPrescList = new ArrayList<>();

			for(Tuple tuple : tuples) {
				Prescription prescription = tuple.get(0, Prescription.class);
//				Medicine medicine = tuple.get(1, Medicine.class);
//				DocDiagnosis docDiagnosis = tuple.get(2, DocDiagnosis.class);
				String patName = tuple.get(3, String.class);
//				String docName = tuple.get(4, String.class);
//				String departmentName = tuple.get(5, String.class);
//				
				Map<String, Object> map = new HashMap<>();

				// tuple에서 데이터 추출하여 map에 넣기
				map.put("prescription", tuple.get(0, Prescription.class));
//				map.put("medicine", tuple.get(1, Medicine.class));
//				map.put("docDiagnosis", tuple.get(2, DocDiagnosis.class));
				map.put("patName", tuple.get(3, String.class));
//				map.put("docName", tuple.get(4, String.class));
//				map.put("departmentName", tuple.get(5, String.class));
				patPrescList.add(map);
			}
			
			return patPrescList;}

		public Map<String, Object> patDiagCheckInfoByDocDiagNum(Long docDiagNum) {
			Tuple tuple = admDslRepository.findPatDiagCheckInfoByDocDiagNum(docDiagNum);
			
			DocDiagnosis docDiag = tuple.get(0, DocDiagnosis.class);
			Patient patient = tuple.get(1, Patient.class);
			String docName = tuple.get(2, String.class);
			String deptName = tuple.get(3, String.class);
			String diseaseName = tuple.get(4, String.class);
			String admDiagContent = tuple.get(5, String.class);
			
			Map<String, Object> map = objectMapper.convertValue(docDiag, Map.class);
			map.put("patName", patient.getPatName());
			map.put("patGender", patient.getPatGender());
			map.put("patJumin", patient.getPatJumin());
			map.put("patTel", patient.getPatTel());
			map.put("patHistory", patient.getPatHistory());
			map.put("patAddress", patient.getPatAddress());
			map.put("docName", docName);
			map.put("deptName", deptName);
			map.put("diseaseName", diseaseName);
			if(docDiag.getDocDiagnosisContent() == null) {
				map.put("docDiagnosisContent", admDiagContent);
			}
			
			return map;
		}

		@Override
		public Map<String, Object> patAdmCheckInfoByAdmNum(Long admNum) {
			Tuple tuple = admDslRepository.findPatAdmCheckInfoByAdmNum(admNum);
			
			Admission admission = tuple.get(0, Admission.class);
			Patient patient = tuple.get(1, Patient.class);
			String docName = tuple.get(2, String.class);
			
			 //입원 기간 계산
		    Date admissionDate = admission.getAdmissionDate();
		    Date dischargeDate = admission.getAdmissionDischargeDate();
		    long diffMillies = dischargeDate.getTime() - admissionDate.getTime();
		    long admPeriod = diffMillies / (1000 * 60 * 60 * 24);
			
			Map<String, Object> map = objectMapper.convertValue(admission, Map.class);
			map.put("patName", patient.getPatName());
			map.put("patGender", patient.getPatGender());
			map.put("patJumin", patient.getPatJumin());
			map.put("patTel", patient.getPatTel());
			map.put("patAddress", patient.getPatAddress());
			map.put("docName", docName);
			map.put("admPeriod", admPeriod);
			
			return map;
		}

		@Override
		public List<Map<String, Object>> patAdmDiagListByAdmNum(Long admNum) {
			List<Tuple> tupes = admDslRepository.findPatAdmDiagInfoByAdmNum(admNum);
			List<Map<String, Object>> admDiagList = new ArrayList<>();
			
			for(Tuple tuple : tupes) {
				Long admRecordNum = tuple.get(0, Long.class);
				String admRecord = tuple.get(1, String.class);
				Date diagDate = tuple.get(2, Date.class);
				String deptName = tuple.get(3, String.class);
				String diseaseName = tuple.get(4, String.class);
				
				Map<String, Object> map = new HashMap<>();
				map.put("admRecordNum", admRecordNum);
				map.put("admRecord", admRecord);
				map.put("diagDate", diagDate);
				map.put("deptName", deptName);
				map.put("diseaseName", diseaseName);
				admDiagList.add(map);
			}
			
			if(admDiagList.isEmpty()) return null;
			
			return admDiagList;
		}

}
