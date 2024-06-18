package com.kosta.care.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
	public List<Map<String, Object>> prevDiagListByPatNum(Long patNum) {
		List<Tuple> tuples = diagnosisRepository.findPrevDiagRecord(patNum);
		List<Map<String, Object>> prevDiagList = new ArrayList<>();
		
		for(Tuple tuple : tuples) {
			DocDiagnosis docDiag = tuple.get(0, DocDiagnosis.class);
			Prescription prescription = tuple.get(1, Prescription.class);
			Long docNum = tuple.get(2, Long.class);
			String docName = tuple.get(3, String.class);
			String medName = tuple.get(4, String.class);
			String testPart = tuple.get(5, String.class);
			String diseaseName = tuple.get(6, String.class);
			
			Map<String, Object> map = objectMapper.convertValue(docDiag, Map.class);
			map.put("preDosage", prescription.getPrescriptionDosage());
			map.put("preDosageTime", prescription.getPrescriptionDosageTimes());
			map.put("preDosageTotal", prescription.getPrescriptionDosageTotal());
			map.put("preHowTake", prescription.getPrescriptionHowTake());
			map.put("docNum", docNum);
			map.put("docName", docName);
			map.put("medName", medName);
			map.put("testPart", testPart);
			map.put("diseaseName", diseaseName);
			prevDiagList.add(map);
		}
		
		if(prevDiagList.isEmpty()) {
			return null;
		}
		return prevDiagList;
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
			testRequest.setTestRequestAcpt("검사요청");
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
            admissionRequestRepository.save(admissionRequest);
		}
		
		if(docDiagDto.isSurChecked()) {
			SurgeryRequest surgeryRequest = new SurgeryRequest();
			surgeryRequest.setSurReason(docDiagDto.getSurReason());
			surgeryRequest.setSurDate(docDiagDto.getSurDate());
			surgeryRequest.setSurPeriod(docDiagDto.getSurPeriod());
			surgeryRequest.setPatNum(docDiagDto.getPatNum());
			surgeryRequestRepository.save(surgeryRequest);
		}
    	
		StringBuilder docDiagAddBuilder = new StringBuilder();
		
		for(PrescriptionDto preDto : docDiagDto.getSelectMedicine()) {
			Prescription prescription = new Prescription();
			prescription.setMedicineNum(preDto.getMedicineNum());
			prescription.setPatNum(docDiagDto.getPatNum());
			prescription.setDocNum(docDiagDto.getDocNum());
			prescription.setPrescriptionDosage(preDto.getPreDosage());
			prescription.setPrescriptionDosageTimes(preDto.getPreDosageTimes());
			prescription.setPrescriptionDosageTotal(preDto.getPreDosageTotal());
			prescription.setPrescriptionHowTake(preDto.getPreHowTake());
			prescription.setPrescriptionDate(new Date(System.currentTimeMillis()));
			prescriptionRepository.save(prescription);
			docDiagAddBuilder.append(prescription.getPrescriptionNum()+",");
		}
		//마지막 콤마 제거
		if(docDiagAddBuilder.length() > 0) { 
			docDiagAddBuilder.deleteCharAt(docDiagAddBuilder.length() - 1);
		}
		docDiagnosis.setPrescriptionNum(docDiagAddBuilder.toString());
		
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
	public List<List<DiagnosisDueDto>> doctorDiagnosisDueList(Long departmentNum,Date date) throws Exception {
		System.out.println(date);
		List<Doctor> doctors = doctorRepository.findByDepartmentNum(departmentNum);
		
		List<List<DiagnosisDueDto>> doctorDiagnosisDueList = new ArrayList<List<DiagnosisDueDto>>();
		for(Doctor doctor : doctors) {
			List<DiagnosisDue> diagnosisDueList = diagnosisDueRepository.findByDocNumAndDiagnosisDueDate(doctor.getDocNum(), date);
			
			List<DiagnosisDueDto> diagnosisDueDtoList = diagnosisDueList.stream().map(due->DiagnosisDueDto.builder()
												.diagnosisDueDate(due.getDiagnosisDueDate())
												.diagnosisDueTime(due.getDiagnosisDueTime())
												.docNum(due.getDocNum())
												.docName(doctor.getName()).build()).collect(Collectors.toList());
												
			doctorDiagnosisDueList.add(diagnosisDueDtoList);
		}
		return doctorDiagnosisDueList;
	}
}
