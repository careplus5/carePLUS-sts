package com.kosta.care.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.kosta.care.entity.NurDiagnosis;

public interface NurseDiagnosisService {
	
	// 진료 환자 목록 조회
		List<Map<String,Object>> diagPatientList(Long nurNum);
		
		// 진료 기록 업데이트
		NurDiagnosis updateNurDiagnosis (Long nurDiagNum, String nurDiagContent, Date nurDiagnosisDate);

}
