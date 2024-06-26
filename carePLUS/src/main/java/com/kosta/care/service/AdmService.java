package com.kosta.care.service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kosta.care.dto.AdmissionRequestDto;
import com.kosta.care.dto.DiagnosisDueDto;
import com.kosta.care.dto.SurgeryRequestDto;
import com.kosta.care.dto.TestRequestDto;
import com.kosta.care.entity.Admission;
import com.kosta.care.entity.Beds;
import com.kosta.care.entity.DocDiagnosis;
import com.kosta.care.entity.Surgery;
import com.kosta.care.entity.Test;


public interface AdmService {
	
	// 진료확인 리스트 
	List<Map<String, Object>> patDiagCheckListByPatNum(Long patNum);
	
	// 진료확인서 정보
	Map<String, Object> patDiagCheckInfoByDocDiagNum(Long docDiagNum);
	
	// 입원 확인 리스트
	List<Map<String, Object>> patAdmCheckListByPatNum(Long patNum);

	// 입퇴원확인서 정보
	Map<String, Object> patAdmCheckInfoByAdmNum(Long admNum);
	
	// 입퇴원확인서 (진료내역)
	List<Map<String, Object>> patAdmDiagListByAdmNum(Long admNum);
	
	List<DocDiagnosis> getConfirmDianosis(Long patNum) throws Exception;
	
	// 입원 확인 리스트
	List<Admission> getConfirmAdmission(Long patNum) throws Exception;

	
	// 진료예약 등록( 환자등록 및 의사진단 )
	void diagnosisRegister(DiagnosisDueDto diagnosisDueDto) throws Exception;
	
	// 환자의 처방전 리스트
	List<Map<String, Object>> getPrescriptionList(Long patNum) throws Exception;
	
	List<TestRequestDto> getTestRequestListByPatNum(Long patNum) throws Exception;

	List<Time> getTestList(String testName, Date testDate) throws Exception;

	Boolean testReserve(Test test, MultipartFile testFile) throws Exception;

	SurgeryRequestDto getSurgeryRequest(Long patNum) throws Exception;
	
	Map<String,Object> operationRoomUse(Date useDate) throws Exception;
	
	Map<String,Object> sureryNurList(Long departmentNum, Date surDate) throws Exception;

	Boolean reserveSurgery(Surgery surgery) throws Exception;

//	List<TestRequestDto> getTestRequestListByPatNum(Long patNum) throws Exception;
//	List<Time> getTestList(String testName, Date testDate) throws Exception;
//	Boolean testReserve(Test test, MultipartFile testFile) throws Exception;
//	SurgeryRequestDto getSurgeryRequest(Long patNum) throws Exception;
//	Map<String,Object> operationRoomUse(Date useDate) throws Exception;
//	Map<String,Object> sureryNurList(Long departmentNum, Date surDate) throws Exception;
//	Boolean reserveSurgery(Surgery surgery) throws Exception;

	AdmissionRequestDto getSearchAdmissionRequestPatient(Long patNum) throws Exception;
	
//	void getPatientAdmissionRegist(Long docNum, String admissionRequestReason, Long patNum, Long admissionRequestPeriod,
//			Long bedsDept, Long bedsWard, Long bedsRoom, Long bedsBed)throws Exception;
	
	void getPatientAdmissionRegist(AdmissionRequestDto admissionRequestDto)throws Exception;
	
	AdmissionRequestDto admissionRequest(Long patNum) throws Exception;

	List<Beds> findByBedsListByDeptnum(Long departmentNum) throws Exception;
	
	Boolean procAdmission(Admission admission) throws Exception;

}
