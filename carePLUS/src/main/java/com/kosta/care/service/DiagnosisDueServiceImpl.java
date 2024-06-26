package com.kosta.care.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosta.care.dto.DiagnosisDueDto;
import com.kosta.care.dto.DocDiagnosisDto;
import com.kosta.care.dto.PrescriptionDto;
import com.kosta.care.entity.AdmissionRequest;
import com.kosta.care.entity.DiagnosisDue;
import com.kosta.care.entity.Disease;
import com.kosta.care.entity.DocDiagnosis;
import com.kosta.care.entity.Doctor;
import com.kosta.care.entity.FavoriteMedicines;
import com.kosta.care.entity.Medicine;
import com.kosta.care.entity.Patient;
import com.kosta.care.entity.Prescription;
import com.kosta.care.entity.SurgeryRequest;
import com.kosta.care.entity.TestRequest;
import com.kosta.care.repository.AdmissionRequestRepository;
import com.kosta.care.repository.DiagnosisDslRepository;
import com.kosta.care.repository.DiagnosisDueRepository;
import com.kosta.care.repository.DocDiagnosisRepository;
import com.kosta.care.repository.DoctorRepository;
import com.kosta.care.repository.FavoriteMedicinesRepository;
import com.kosta.care.repository.MedicineRepository;
import com.kosta.care.repository.PatientRepository;
import com.kosta.care.repository.PrescriptionRepository;
import com.kosta.care.repository.SurgeryRequestRepository;
import com.kosta.care.repository.TestRequestRepository;
import com.querydsl.core.Tuple;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiagnosisDueServiceImpl implements DiagnosisDueService {
	
	private final DiagnosisDueRepository diagnosisDueRepository;
	private final PatientRepository patientRepository;
	private final DoctorRepository doctorRepository;
	private final DiagnosisDslRepository diagnosisRepository;
	private final FavoriteMedicinesRepository favoriteMedicinesRepository;
	private final MedicineRepository medicineRepository;
	private final DocDiagnosisRepository docDiagnosisRepository;
	private final TestRequestRepository testRequestRepository;
	private final AdmissionRequestRepository admissionRequestRepository;
	private final SurgeryRequestRepository surgeryRequestRepository;
	private final PrescriptionRepository prescriptionRepository;
	
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
			Long deptNum = tuple.get(4, Long.class);

			Map<String, Object> map = objectMapper.convertValue(diagDue, Map.class);
			map.put("patName", patName);
			map.put("docDiagNum", docDiagNum);
			map.put("docDiagState", docDiagState);
			map.put("deptNum", deptNum);
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
		Patient patient = tuple.get(1, Patient.class);
		DocDiagnosis docDiagnosis = tuple.get(2, DocDiagnosis.class);

		Optional<DocDiagnosis> odocDiagNum = docDiagnosisRepository.findById(docDiagNum);

		Map<String, Object> map = objectMapper.convertValue(diagDue, Map.class);
		map.put("patName", patient.getPatName());
		map.put("patJumin", patient.getPatJumin());
		map.put("docDiagState", docDiagnosis.getDocDiagnosisState());
		map.put("docDiagNum", odocDiagNum.get().getDocDiagnosisNum());
		
		return map;
	}

	@Override
	public void updateDocDiagnosisState(Long docDiagNum, String newState) {
		diagnosisRepository.modifyDocDiagnosisState(docDiagNum, newState);
	}
	

	@Override
	public List<Map<String, Object>> diseaseListByDeptNum(Long docNum) throws Exception {
		Optional<Doctor> odoctor = doctorRepository.findById(docNum);
		if(odoctor.isEmpty()) throw new Exception("부서번호 없음");
		
		List<Tuple> tuples = diagnosisRepository.findDiseaseListByDeptNum(odoctor.get().getDepartmentNum());
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

	@Override
	public List<Medicine> medicineList(String medSearchType, String medSearchKeyword) {
		
		List<Medicine> medicineList = new ArrayList<>();
		
		if(medSearchKeyword == null || medSearchKeyword.trim().equals("")) {
			medicineList = medicineRepository.findAll();
		} else {
			if(medSearchType.equals("medNum")) {
				medicineList = medicineRepository.findByMedicineNum(medSearchKeyword);
			} else if(medSearchType.equals("medEnName")) {
				medicineList = medicineRepository.findByMedicineEnNameContainingIgnoreCase(medSearchKeyword);
			} else if(medSearchType.equals("medKorName")) {
				medicineList = medicineRepository.findByMedicineKorNameContainingIgnoreCase(medSearchKeyword);
			}
		}
		
		if(medicineList.isEmpty()) {
			return null;
		}
		return medicineList;
	}

	@Override
	public List<Map<String, Object>> favMedicineList(Long docNum) {
		List<Tuple> tuples = diagnosisRepository.findFavMedicineListByDocNum(docNum);
		List<Map<String, Object>> favMedicineList = new ArrayList<>();
		
		for(Tuple tuple : tuples) {
			FavoriteMedicines favoriteMedicines = tuple.get(0, FavoriteMedicines.class);
			String medicineKorName = tuple.get(1, String.class);

			Map<String, Object> map = objectMapper.convertValue(favoriteMedicines, Map.class);
			map.put("medicineKorName", medicineKorName);
			favMedicineList.add(map);
		}
		
		if(favMedicineList.isEmpty()) {
			return null;
		}
		return favMedicineList;
	}

	@Override
	public Boolean addFavMedicine(Long docNum, String medicineNum) {
		FavoriteMedicines favMed = diagnosisRepository.findFavoriteMedicines(docNum, medicineNum);
		if(favMed == null) {
			favoriteMedicinesRepository.save(FavoriteMedicines.builder()
													.docNum(docNum)
													.medicineNum(medicineNum)
													.build());
			return true;
		} else {
			favoriteMedicinesRepository.deleteById(favMed.getFavoriteMedicinesNum());;
			return false;
		}
	}

	@Override
	public Boolean submitDiagnosis(DocDiagnosisDto docDiagDto) {
		DocDiagnosis docDiagnosis = docDiagDto.toDocDiagnosis();
		
		if(docDiagDto.isTestChecked()) {
			TestRequest testRequest = new TestRequest();
			testRequest.setTestName(docDiagDto.getTestType());
			testRequest.setTestPart(docDiagDto.getTestRequest());
			testRequest.setDocNum(docDiagDto.getDocNum());
			testRequest.setPatNum(docDiagDto.getPatNum());
			testRequest.setTestRequestAcpt("request");
			testRequest.setDocDiagnosisNum(docDiagDto.getDocDiagnosisNum());
			testRequestRepository.save(testRequest);
			docDiagnosis.setTestRequestNum(testRequest.getTestRequestNum());
		}
		
		if(docDiagDto.isAdmChecked()) {
			AdmissionRequest admissionRequest = new AdmissionRequest();
			admissionRequest.setAdmissionRequestReason(docDiagDto.getAdmReason());
			admissionRequest.setAdmissionRequestPeriod(docDiagDto.getAdmPeriod());
			admissionRequest.setPatNum(docDiagDto.getPatNum());
			admissionRequest.setDocNum(docDiagDto.getDocNum());
			admissionRequest.setDiagnosisNum(docDiagDto.getDocDiagnosisNum());
			admissionRequest.setDepartmentNum(docDiagDto.getDeptNum());
			admissionRequest.setAdmissionRequestAcpt("wait");
            admissionRequestRepository.save(admissionRequest);
		}
		
		if(docDiagDto.isSurChecked()) {
			SurgeryRequest surgeryRequest = new SurgeryRequest();
			surgeryRequest.setSurReason(docDiagDto.getSurReason());
			surgeryRequest.setSurDate(docDiagDto.getSurDate());
			surgeryRequest.setSurPeriod(docDiagDto.getSurPeriod());
			surgeryRequest.setDepartmentNum(docDiagDto.getDeptNum());
			surgeryRequest.setPatNum(docDiagDto.getPatNum());
			surgeryRequest.setDocNum(docDiagDto.getDocNum());
			surgeryRequest.setSurgeryRequestAcpt("wait");
			surgeryRequestRepository.save(surgeryRequest);
		}
    	
		StringBuilder medNumAdd = new StringBuilder();
		StringBuilder preDosageAdd = new StringBuilder();
		StringBuilder preDosageTimesAdd = new StringBuilder();
		StringBuilder preDosageTotalAdd = new StringBuilder();
		StringBuilder preHowTakeAdd = new StringBuilder();
		
		for(PrescriptionDto preDto : docDiagDto.getSelectMedicine()) {
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
		prescription.setPatNum(docDiagDto.getPatNum());
		prescription.setDocNum(docDiagDto.getDocNum());
		prescription.setPrescriptionDosage(preDosageAdd.toString());
		prescription.setPrescriptionDosageTimes(preDosageTimesAdd.toString());
		prescription.setPrescriptionDosageTotal(preDosageTotalAdd.toString());
		prescription.setPrescriptionHowTake(preHowTakeAdd.toString());
		prescription.setPrescriptionDate(new Date(System.currentTimeMillis()));
		prescriptionRepository.save(prescription);
		
		docDiagnosis.setPrescriptionNum(prescription.getPrescriptionNum());

		DocDiagnosis updateDocDiag = docDiagnosisRepository.save(docDiagnosis);
		
		return updateDocDiag != null;
	}

	// 진료예약 조회
	@Override
	public List<DiagnosisDue> diagSearchAll() {
		return diagnosisDueRepository.findAll();
	}

	// 모달로 의사 스케줄 조회 (진료예약) 
	@Override
	public Map<String, Object> doctorDiagnosisDueList(Long departmentNum,Date date) throws Exception {
		System.out.println(date);
		
		Map<String, Object> res = new HashMap<>();
		
		List<Doctor> doctors = doctorRepository.findByDepartmentNum(departmentNum);
		
		res.put("doctors", doctors);
		List<DiagnosisDueDto> diagnosisDueDtoList = new ArrayList<DiagnosisDueDto>();
		for(Doctor doctor : doctors) {
			List<DiagnosisDue> diagnosisDueList = diagnosisDueRepository.findByDocNumAndDiagnosisDueDate(doctor.getDocNum(), date);
			if(diagnosisDueList.size() == 0) {
				continue;
			} else {
				for(DiagnosisDue due : diagnosisDueList) {
					diagnosisDueDtoList.add(DiagnosisDueDto.builder()
											.diagnosisDueNum(due.getDiagnosisDueNum())
											.diagnosisDueDate(due.getDiagnosisDueDate())
											.diagnosisDueTime(due.getDiagnosisDueTime())
											.docNum(due.getDocNum())
											.docName(doctor.getName()).build());
				}
			}
		}
		res.put("diagnosisDueDtoList", diagnosisDueDtoList);
		return res;
	}


	@Override
	public List<Map<String, Object>> docPatListByDocNum(Long docNum, String searchType, String searchKeyword) {
		List<Tuple> tuples = diagnosisRepository.findDocDiagPatListByDocNum(docNum, searchType, searchKeyword);
		List<Map<String, Object>> docPatList = new ArrayList<>();
		
		for(Tuple tuple : tuples) {
			DocDiagnosis docDiagnosis = tuple.get(0, DocDiagnosis.class);
			Patient patient = tuple.get(1, Patient.class);
			String diseaseName = tuple.get(2, String.class);
			String admState = tuple.get(3, String.class);
			String surState = tuple.get(4, String.class);

			Map<String, Object> map = objectMapper.convertValue(docDiagnosis, Map.class);
			map.put("patName", patient.getPatName());
			map.put("patJumin", patient.getPatJumin());
			map.put("patGender", patient.getPatGender());
			map.put("diseaseName", diseaseName);
			map.put("admState", admState);
			map.put("surState", surState);
			docPatList.add(map);
		}
		
		if(docPatList.isEmpty()) {
			return null;
		}
		return docPatList;
	}

	@Override
	public List<Map<String, Object>> patDiagListByPatNum(Long patNum, String searchType, String searchKeyword) {
		List<Tuple> tuples = diagnosisRepository.findPatDiagListByPatNum(patNum, searchType, searchKeyword);
		List<Map<String, Object>> patDiagList = new ArrayList<>();
		
		for(Tuple tuple : tuples) {
			DocDiagnosis docDiag = tuple.get(0, DocDiagnosis.class);
			Prescription prescription = tuple.get(1, Prescription.class);
			Long docNum = tuple.get(2, Long.class);
			String docName = tuple.get(3, String.class);
			String medName = tuple.get(4, String.class);
			String testPart = tuple.get(5, String.class);
			Long diseaseNum = tuple.get(6, Long.class);
			String diseaseName = tuple.get(7, String.class);
			
			String[] medNums = prescription==null ? null : prescription.getMedicineNum().split(",");
			StringBuilder medNameAdd = new StringBuilder();
			if(medNums != null) {
				for(String med : medNums) {
					Optional<Medicine> omed = medicineRepository.findById(med);
					medName = omed.get().getMedicineKorName();
					medNameAdd.append(medName+",");
				}
				//마지막 콤마 제거
				if(medNameAdd.length() > 0) { 
					medNameAdd.deleteCharAt(medNameAdd.length() - 1);
				}
			}
			
			Map<String, Object> map = objectMapper.convertValue(docDiag, Map.class);
			map.put("preDosage", prescription==null? null : prescription.getPrescriptionDosage());
			map.put("preDosageTime", prescription==null? null : prescription.getPrescriptionDosageTimes());
			map.put("preDosageTotal", prescription==null? null : prescription.getPrescriptionDosageTotal());
			map.put("preHowTake", prescription==null? null : prescription.getPrescriptionHowTake());
			map.put("docNum", docNum);
			map.put("docName", docName);
			map.put("medName", medNameAdd.toString());
			map.put("testPart", testPart);
			map.put("diseaseNum", diseaseNum);
			map.put("diseaseName", diseaseName);
			patDiagList.add(map);
		}
		
		if(patDiagList.isEmpty()) {
			return null;
		}
		return patDiagList;
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
	    DocDiagnosis docDiagnosis = DocDiagnosis.builder().docNum(diagnosisDueDto.getDocNum())
								    	.docNum(diagnosisDueDto.getDocNum())
								    	.patNum(diagnosisDueDto.getPatNum())
								    	.docDiagnosisState("wait")
								    	.docDiagnosisDate(diagnosisDue.getDiagnosisDueDate())
								    	.diagnosisDueNum(diagnosisDue.getDiagnosisDueNum())
								    	.build();

	    docDiagnosisRepository.save(docDiagnosis);
	}

}
