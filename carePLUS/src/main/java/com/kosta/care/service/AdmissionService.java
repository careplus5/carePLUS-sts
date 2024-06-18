package com.kosta.care.service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Map;

import com.kosta.care.dto.AdmDiagnosisDto;
import com.kosta.care.dto.DocDiagnosisDto;
import com.kosta.care.entity.Admission;
import com.kosta.care.entity.AdmissionRecord;

public interface AdmissionService {
	
//	//대기환자목록 조회
//		List<Map<String, Object>> diagDueListByDocNum(Long docNum);
//		//환자접수정보 조회
//		Map<String, Object> diagDueInfoByDocDiagNum(Long docDiagNum);
//		//환자상태 업데이트
//		void updateDocDiagnosisState(Long docDiagNum, String newState);
//		//부서에 따른 병명리스트 조회
//		List<Map<String, Object>> diseaseListByDeptNum(Long deptNum) throws Exception;
//		//약품리스트 조회
//		List<Medicine> medicineList(String medSearchType, String medSearchKeyword);
//		//즐겨찾기 약품리스트 조회
//		List<Map<String, Object>> favMedicineList(Long docNum);
//		//즐겨찾기 약품 등록
//		Boolean addFavMedicine(Long docNum, String medicineNum);
	
	// 입원 중인 환자 목록 조회
	List<Map<String,Object>> admPatientList(Long nurNum);
	
	// 의사 입원 일지 조회
	List<Map<String,Object>> admPatientDoctorRecordList(Long admissionNum);
	
	// 간호사 입원 일지 조회
	List<Map<String,Object>> admPatientNurseRecordList(Long admissionNum);
	
	// 퇴원처리
	Boolean updateAdmissionDischarge(Long admissionNum,String admissionDischargeOpinion, Date admissionDischargeDate);
	
	//입원진료-입원 환자 목록 조회
	List<Map<String,Object>> admDiagPatientList(Long docNum);
	
	//입원진료-입원환자정보 조회
	Map<String,Object> admDiagPatInfo(Long admNum) throws Exception;
	
	//간호사 입원 일지 추가
	 AdmissionRecord saveNurseAdmissionRecord(AdmissionRecord record);
	 
	//입원진료-입원 환자 목록 조회
		List<Map<String,Object>> dailyPrescriptionList(Long patNum);
		
	// 처방 상태 업데이
		Boolean updatePrescDiary(String patNum, String prescriptionNum, String buttonNum, String nurNum, String diaryStatus, Time diaryTime);
		
	//입원진료-초기진단기록 조회
	Map<String, Object> firstDiagRecordInfo(Long patNum);
	
	//입원진료-간호사 입원일지 조회
	List<Map<String, Object>> admNurRecordList(Long admNum);

	//입원진료-의사 입원진단기록 조회
	List<Map<String, Object>> admDiagRecordList(Long admNum);
	
	//입원진료-입원진료 내용 전송
	Boolean submitAdmDiag(AdmDiagnosisDto admDiagDto);
}
