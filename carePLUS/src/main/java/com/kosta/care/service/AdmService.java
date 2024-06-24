package com.kosta.care.service;

import java.util.List;
import java.util.Map;

import com.kosta.care.dto.DiagnosisDueDto;
import com.kosta.care.entity.Admission;

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
	
	// 진료예약 등록( 환자등록 및 의사진단 )
	void diagnosisRegister(DiagnosisDueDto diagnosisDueDto) throws Exception;
	
	// 환자의 처방전 리스트
	List<Map<String, Object>> getPrescriptionList(Long patNum) throws Exception;

}
