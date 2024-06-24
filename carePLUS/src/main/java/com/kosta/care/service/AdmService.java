package com.kosta.care.service;

import java.util.List;
import java.util.Map;

import com.kosta.care.dto.DiagnosisDueDto;
import com.kosta.care.entity.Admission;

public interface AdmService {
	
	// 진료확인 리스트 
	List<Map<String, Object>> patDiagCheckListByPatNum(Long patNum);
	
	// 입원 확인 리스트
	List<Admission> getConfirmAdmission(Long patNum) throws Exception;
	// 진료예약 등록( 환자등록 및 의사진단 )
	void diagnosisRegister(DiagnosisDueDto diagnosisDueDto) throws Exception;
	
	// 환자의 처방전 리스트
	List<Map<String, Object>> getPrescriptionList(Long patNum) throws Exception;

}
