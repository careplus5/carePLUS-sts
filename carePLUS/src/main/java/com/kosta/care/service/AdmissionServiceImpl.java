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
import com.kosta.care.dto.AdmDiagnosisDto;
import com.kosta.care.dto.PrescriptionDto;
import com.kosta.care.entity.Admission;
import com.kosta.care.entity.AdmissionRecord;
import com.kosta.care.entity.AdmissionRequest;
import com.kosta.care.entity.Beds;
import com.kosta.care.entity.DocDiagnosis;
import com.kosta.care.entity.Doctor;
import com.kosta.care.entity.Nurse;
import com.kosta.care.entity.Patient;
import com.kosta.care.entity.Prescription;
import com.kosta.care.entity.PrescriptionDiary;
import com.kosta.care.entity.SurgeryRequest;
import com.kosta.care.entity.TestRequest;
import com.kosta.care.repository.AdmissionDslRepository;
import com.kosta.care.repository.AdmissionRecordRepository;
import com.kosta.care.repository.AdmissionRepository;
import com.kosta.care.repository.BedsRepository;
import com.kosta.care.repository.DiagnosisDslRepository;
//github.com/careplus5/carePLUS-sts.git
import com.kosta.care.repository.DiagnosisDueRepository;
import com.kosta.care.repository.DocDiagnosisRepository;
import com.kosta.care.repository.DoctorRepository;
import com.kosta.care.repository.FavoriteMedicinesRepository;
import com.kosta.care.repository.MedicineRepository;
import com.kosta.care.repository.NurseRepository;
import com.kosta.care.repository.PatientRepository;
import com.kosta.care.repository.PrescriptionDiaryRepository;
import com.kosta.care.repository.PrescriptionRepository;
import com.kosta.care.repository.SurgeryRequestRepository;
import com.kosta.care.repository.TestRequestRepository;
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
	private final DocDiagnosisRepository docDiagnosisRepository;
	private final TestRequestRepository testRequestRepository;
	private final SurgeryRequestRepository surgeryRequestRepository;
	private final PrescriptionRepository prescriptionRepository;
	private final AdmissionRecordRepository admissionRecordRepository;
	private final BedsRepository bedsRepository;

	private final ObjectMapper objectMapper;


	@Override
	public List<Map<String, Object>> admPatientList(Long nurNum) {
		// admission내역


		List<Tuple> tuples = admRepository.findAdmPatientByNurNum(nurNum);
		System.out.println("patient list:"+tuples.toString());
		List<Map<String, Object>> admList = new ArrayList<>();

		for(Tuple tuple : tuples) {
			System.out.println(nurNum+"찾아보자이");
			Admission admission = tuple.get(0,Admission.class);
			String docDepartment = tuple.get(1, String.class);
			String docName = tuple.get(2,String.class);
			String patName = tuple.get(3,String.class);
			String jumin= tuple.get(4,String.class);
			Map<String, Object> map = new HashMap<>();
			map.put("admission", admission);
			map.put("docDepartment", docDepartment);
			map.put("patJumin", jumin.split("-")[0]);
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
			String docName = tuple.get(2,String.class);
			Map<String, Object> map = new HashMap<>();
			map.put("record", record);
			map.put("docNum", docNum);
			map.put("docName", docName);
			recordList.add(map);
		}

		return recordList;
	}

	//간호사 입원 기록
	@Override
	public List<Map<String, Object>> admPatientNurseRecordList(Long admissionNum) {
		// admission내역

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
			
			// 퇴원시 Beds 상태 false로 변경
			Long bedsNum = admission.getBedsNum();
			Optional<Beds> obed = bedsRepository.findById(bedsNum);
			Beds bed = obed.get();
			bed.setBedsIsUse(false);
			bedsRepository.save(bed);

			if("ing".equals(admission.getAdmissionStatus())) {
				admission.setAdmissionDischargeDate(admissionDischargeDate);
				admission.setAdmissionDischargeOpinion(admissionDischargeOpinion);
				admission.setAdmissionStatus("end");
				admission.setAdmissionDiagState("end");
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
	public Boolean updatePrescDiary(String patNum, Long prescriptionNum, String buttonNum, String nurNum, String diaryStatus, Time diaryTime) {
		try {
			//			Admission admission = admRepository.findByAdmissionNum(admissionNum);
			// prescription , 버튼 수
			PrescriptionDiary diary = new PrescriptionDiary();
			diary.setNurNum(Long.parseLong(nurNum));
			diary.setPatNum(Long.parseLong(patNum));
			System.out.println(prescriptionNum+": 이상 무");
			String prescriptionDiaryFre1 = diaryTime.toString()+", "+diaryStatus;

			System.out.println(prescriptionDiaryFre1);
			if(buttonNum.equals("1")) {
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
			Doctor doctor = tuple.get(2, Doctor.class);

			Map<String, Object> map = objectMapper.convertValue(admission, Map.class);
			map.put("patNum", patient.getPatNum());
			map.put("patName", patient.getPatName());
			map.put("docName", doctor.getDocName());
			map.put("deptNum", doctor.getDepartmentNum());
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

		//의사진료에 입원진료 insert
		Tuple tuple2 = admRepository.findFirstDiagRecordByPatNum(patient.getPatNum());
		Long diseaseNum = tuple2.get(2, Long.class);

		DocDiagnosis docDiag = new DocDiagnosis();
		docDiag.setDocDiagnosisKind("adm");
		docDiag.setDocDiagnosisState("ing");
		docDiag.setDocNum(admission.getDocNum());
		docDiag.setPatNum(admission.getPatNum());
		docDiag.setDiseaseNum(diseaseNum);

		docDiagnosisRepository.save(docDiag);
		map.put("admDiagNum", docDiag.getDocDiagnosisNum());

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
		System.out.println("해당 환자의 처방 리스트입니다: "+tuples.toString());

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


	@Override
	public Map<String, Object> firstDiagRecordInfo(Long patNum) {
		Tuple tuple = admRepository.findFirstDiagRecordByPatNum(patNum);

		String diseaseName = tuple.get(0, String.class);
		String diagContent = tuple.get(1, String.class);

		Map<String, Object> map = new HashMap<>();
		map.put("diseaseName", diseaseName);
		map.put("diagContent", diagContent);

		return map;
	}
	
	@Override
	public List<Map<String, Object>> admNurRecordList(Long admNum) {
		List<Tuple> tuples = admRepository.findAdmNurRecordByAdmNum(admNum);
		List<Map<String, Object>> admNurRecordList = new ArrayList<>();

		for(Tuple tuple : tuples) {
			AdmissionRecord admissionRecord = tuple.get(0, AdmissionRecord.class);
			Nurse nurse = tuple.get(1, Nurse.class);

			Map<String, Object> map = objectMapper.convertValue(admissionRecord, Map.class);
			map.put("nurNum", nurse.getNurNum());
			map.put("nurName", nurse.getNurName());
			admNurRecordList.add(map);
		}

		if(admNurRecordList.isEmpty()) return null;

		return admNurRecordList;
	}
	
	@Override
	public List<Map<String, Object>> admDiagRecordList(Long admNum) {
		List<Tuple> tuples = admRepository.findAdmDiagRecordByAdmNum(admNum);
		List<Map<String, Object>> admDiagRecordList = new ArrayList<>();

		for(Tuple tuple : tuples) {
			AdmissionRecord admissionRecord = tuple.get(0, AdmissionRecord.class);
			Doctor doctor = tuple.get(1, Doctor.class);

			Map<String, Object> map = objectMapper.convertValue(admissionRecord, Map.class);
			map.put("docNum", doctor.getDocNum());
			map.put("docName", doctor.getDocName());
			admDiagRecordList.add(map);
		}

		if(admDiagRecordList.isEmpty()) return null;

		return admDiagRecordList;
	}

	@Override
	public Boolean submitAdmDiag(AdmDiagnosisDto admDiagDto) {
		AdmissionRecord admDiagRecord = admDiagDto.toAdmDiagRecord();
		Optional<DocDiagnosis> odocDiag = docDiagnosisRepository.findById(admDiagDto.getDocDiagnosisNum());
		DocDiagnosis docDiag = odocDiag.get();

		if(admDiagDto.isTestChecked()) {
			TestRequest testRequest = new TestRequest();
			testRequest.setTestName(admDiagDto.getTestType());
			testRequest.setTestPart(admDiagDto.getTestRequest());
			testRequest.setDocNum(admDiagDto.getDocNum());
			testRequest.setPatNum(admDiagDto.getPatNum());
			testRequest.setTestRequestAcpt("검사요청");
			testRequest.setDocDiagnosisNum(admDiagDto.getDocDiagnosisNum());
			testRequestRepository.save(testRequest);
			docDiag.setTestRequestNum(testRequest.getTestRequestNum());
		}

		if(admDiagDto.isSurChecked()) {
			SurgeryRequest surRequest = new SurgeryRequest();
			surRequest.setSurReason(admDiagDto.getSurReason());
			surRequest.setSurDate(admDiagDto.getSurDate());
			surRequest.setSurPeriod(admDiagDto.getSurPeriod());
			surRequest.setPatNum(admDiagDto.getPatNum());
			surRequest.setDepartmentNum(admDiagDto.getDeptNum());
			surRequest.setDocNum(admDiagDto.getDocNum());
			surRequest.setSurgeryRequestAcpt("wait");
			surgeryRequestRepository.save(surRequest);
		}

		StringBuilder medNumAdd = new StringBuilder();
		StringBuilder preDosageAdd = new StringBuilder();
		StringBuilder preDosageTimesAdd = new StringBuilder();
		StringBuilder preDosageTotalAdd = new StringBuilder();
		StringBuilder preHowTakeAdd = new StringBuilder();

		for(PrescriptionDto preDto : admDiagDto.getSelectMedicine()) {
			medNumAdd.append(preDto.getMedicineNum()+",");
			preDosageAdd.append(preDto.getPreDosage()+",");
			preDosageTimesAdd.append(preDto.getPreDosageTimes()+",");
			preDosageTotalAdd.append(preDto.getPreDosageTotal()+",");
			preHowTakeAdd.append(preDto.getPreHowTake()+",");
		}
		//마지막 콤마 제거
		if(medNumAdd.length() > 0) { 
			medNumAdd.deleteCharAt(medNumAdd.length() - 1);
		}
		if(preDosageAdd.length() > 0) { 
			preDosageAdd.deleteCharAt(preDosageAdd.length() - 1);
		}
		if(preDosageTimesAdd.length() > 0) { 
			preDosageTimesAdd.deleteCharAt(preDosageTimesAdd.length() - 1);
		}
		if(preDosageTotalAdd.length() > 0) { 
			preDosageTotalAdd.deleteCharAt(preDosageTotalAdd.length() - 1);
		}
		if(preHowTakeAdd.length() > 0) { 
			preHowTakeAdd.deleteCharAt(preHowTakeAdd.length() - 1);
		}

		Prescription prescription = new Prescription();
		prescription.setMedicineNum(medNumAdd.toString());
		prescription.setPatNum(admDiagDto.getPatNum());
		prescription.setDocNum(admDiagDto.getDocNum());
		prescription.setPrescriptionDosage(preDosageAdd.toString());
		prescription.setPrescriptionDosageTimes(preDosageTimesAdd.toString());
		prescription.setPrescriptionDosageTotal(preDosageTotalAdd.toString());
		prescription.setPrescriptionHowTake(preHowTakeAdd.toString());
		prescription.setPrescriptionDate(new Date(System.currentTimeMillis()));
		prescriptionRepository.save(prescription);

		docDiag.setPrescriptionNum(prescription.getPrescriptionNum());
		docDiag.setDocDiagnosisState("end");

		docDiagnosisRepository.save(docDiag);

		AdmissionRecord updateAdmDiag = admissionRecordRepository.save(admDiagRecord);

		//입원진료 상태변경
		Optional<Admission> oadm = admissionRepository.findById(admDiagDto.getAdmissionNum());
		if (oadm.isPresent()) {
			Admission admission = oadm.get();
			admission.setAdmissionDiagState("wait");
			admissionRepository.save(admission);
		} 

		return updateAdmDiag != null;
	}

	@Override
	public Tuple patientDischargeRegist(Long patNum) throws Exception {

		return admRepository.findByPatNum(patNum);
	}
}
