package com.kosta.care.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosta.care.entity.Admission;
import com.kosta.care.entity.AdmissionRecord;
import com.kosta.care.entity.AdmissionRequest;
import com.kosta.care.entity.Patient;
import com.kosta.care.entity.Prescription;
import com.kosta.care.entity.PrescriptionDiary;
import com.kosta.care.repository.AdmissionDslRepository;
import com.kosta.care.repository.AdmissionRecordRepository;
import com.kosta.care.repository.AdmissionRepository;
import com.kosta.care.repository.DiagnosisDslRepository;
//github.com/careplus5/carePLUS-sts.git
import com.kosta.care.repository.DiagnosisDueRepository;
import com.kosta.care.repository.DoctorRepository;
import com.kosta.care.repository.FavoriteMedicinesRepository;
import com.kosta.care.repository.MedicineRepository;
import com.kosta.care.repository.NurseRepository;
import com.kosta.care.repository.PatientRepository;
import com.kosta.care.repository.PrescriptionDiaryRepository;
import com.querydsl.core.Tuple;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdmissionServiceImpl implements AdmissionService {


	private final DiagnosisDueRepository diagnosisDueRepository;
	private final NurseRepository nurRepository;
	private final PrescriptionDiaryRepository diaryRepository;
	private final AdmissionDslRepository admRepository;
	private final AdmissionRepository admissionRepository;
	private final PatientRepository patientRepository;
	private final DoctorRepository doctorRepository;
	private final DiagnosisDslRepository diagnosisRepository;
	private final FavoriteMedicinesRepository favoriteMedicinesRepository;
	private final MedicineRepository medicineRepository;
	private final AdmissionRecordRepository adminRecordRepository;
	
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
			Long docNum = tuple.get(1,Long.class);
			System.out.println("의사 진단 결과: "+docNum);
//			System.out.println("이름은"+docName);
			  Map<String, Object> map = new HashMap<>();
			map.put("record", record);
			map.put("docNum", docNum);
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
	
	 @Transactional
	@Override
	public Boolean updatePrescDiary(String patNum, String prescriptionNum, String buttonNum, String nurNum, String diaryStatus, Time diaryTime) {
		try {
//			Admission admission = admRepository.findByAdmissionNum(admissionNum);
			// prescription , 버튼 수
			PrescriptionDiary diary = new PrescriptionDiary();
			diary.setNurNum(Long.parseLong(nurNum));
			System.out.println(nurNum+": 이상 무");
			diary.setPatNum(Long.parseLong(patNum));
			System.out.println(patNum+": 이상 무");
			System.out.println(diaryTime+": 이상 무");
			diary.setPrescriptionNum(prescriptionNum);
			System.out.println(prescriptionNum+": 이상 무");
			  String prescriptionDiaryFre1 = diaryTime.toString()+", "+diaryStatus;
			  System.out.println(prescriptionDiaryFre1+": 이상 무");

			System.out.println(prescriptionDiaryFre1);
			if(buttonNum.equals("1")) {
				System.out.println(buttonNum+": 이상 무");
				System.out.println(prescriptionDiaryFre1.toString());
				diaryRepository.updateDiary1(prescriptionNum, prescriptionDiaryFre1);
			} else if(buttonNum.equals("2")) {
				diary.setPrescriptionDiaryFre2(diaryTime+", "+diaryStatus);
				diaryRepository.updateDiary2(prescriptionNum,prescriptionDiaryFre1);
			} else if(buttonNum.equals("3")) {
				diary.setPrescriptionDiaryFre3(diaryTime+", "+diaryStatus);
				diaryRepository.updateDiary3(prescriptionNum,prescriptionDiaryFre1);
			}
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	
	}
	

	@Override
	public List<Map<String, Object>> admDiagPatientList(Long docNum) {
		List<Tuple> tuples = admRepository.findAdmPatListByDocNum(docNum);
		List<Map<String, Object>> admDiagPatList = new ArrayList<>();
		
		for(Tuple tuple : tuples) {
			Admission admission = tuple.get(0, Admission.class);
			Patient patient = tuple.get(1, Patient.class);

			Map<String, Object> map = objectMapper.convertValue(admission, Map.class);
			map.put("patNum", patient.getPatNum());
			map.put("patName", patient.getPatName());
			admDiagPatList.add(map);
		}
		
		if(admDiagPatList.isEmpty()) {
			return null;
		}
		return admDiagPatList;
	}


	@Override
	public Map<String, Object> admDiagPatInfo(Long admNum) throws Exception {
		Tuple tuple = admRepository.findAdmDiagPatInfoByAdmNum(admNum);
		
		Admission admission = tuple.get(0, Admission.class);
		Patient patient = tuple.get(1, Patient.class);
		AdmissionRequest admRequest = tuple.get(2, AdmissionRequest.class);
		
		Optional<Admission> oadmission = admissionRepository.findById(admNum);
		
		if(oadmission.isEmpty()) throw new Exception("입원정보 없음");
		
		String newState = "ing";
		admission.setAdmissionDiagState(newState);
		
		Map<String, Object> map = objectMapper.convertValue(admission, Map.class);
		map.put("patNum", patient.getPatNum());
		map.put("patName", patient.getPatName());
		map.put("patJumin", patient.getPatJumin());
		map.put("admPeriod", admRequest.getAdmissionRequestPeriod());
		map.put("admReason", admRequest.getAdmissionRequestReason());
		
		//입원환자 입원진료상태 업데이트
		admissionRepository.save(admission);
		
		return map;
	}
	@Override
	public AdmissionRecord saveNurseAdmissionRecord(AdmissionRecord record) {
		return adminRecordRepository.save(record);
	}
	
	
	// 환자 처방전 리스트
	@Override
	public List<Map<String, Object>> dailyPrescriptionList(Long patNum) {
		// 처방전 테이블, 처방 일지 테이블

		List<Tuple> tuples = admRepository.findDailyPrescriptionListByPatNum(patNum);
		System.out.println(tuples.toString()+"zz");
		
//		Admission admission = tuple.get(0, Admission.class);
//		Patient patient = tuple.get(1, Patient.class);
//		AdmissionRequest admRequest = tuple.get(2, AdmissionRequest.class);
//		
List<Map<String, Object>> dailyPrescList = new ArrayList<>();
		
		for(Tuple tuple : tuples) {
			Prescription prescription = tuple.get(0, Prescription.class);
			PrescriptionDiary diary = tuple.get(1, PrescriptionDiary.class);
			Map<String, Object> map = objectMapper.convertValue(prescription, Map.class);
			String prescFre1 = diary.getPrescriptionDiaryFre1();
			String prescFre2 = diary.getPrescriptionDiaryFre2();
			String prescFre3 = diary.getPrescriptionDiaryFre3();
			
			map.put("prescFre1", diary.getPrescriptionDiaryFre1());
		    map.put("prescFre2", diary.getPrescriptionDiaryFre2());
		    map.put("prescFre3", diary.getPrescriptionDiaryFre3());
		    
			dailyPrescList.add(map);
			System.out.println("넣은 값이 "+dailyPrescList);
		}
		
		return dailyPrescList;
	}
}
