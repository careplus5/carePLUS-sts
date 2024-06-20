package com.kosta.care.service;

import java.util.List;
import java.util.Map;

public interface NurseDiagnosisService {
	
	// 진료 환자 목록 조회
		List<Map<String,Object>> diagPatientList(Long nurNum);

}
