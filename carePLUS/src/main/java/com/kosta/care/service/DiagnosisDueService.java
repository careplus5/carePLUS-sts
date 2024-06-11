package com.kosta.care.service;

import java.util.List;
import java.util.Map;

import com.kosta.care.dto.DocDiagnosisDto;
import com.kosta.care.entity.Medicine;

public interface DiagnosisDueService {
	
	//대기환자목록 조회
	List<Map<String, Object>> diagDueListByDocNum(Long docNum);
	//환자접수정보 조회
	Map<String, Object> diagDueInfoByDocDiagNum(Long docDiagNum);
	//환자상태 업데이트
	void updateDocDiagnosisState(Long docDiagNum, String newState);
	//환자 이전진료기록 조회
	List<Map<String, Object>> prevDiagListByPatNum(Long patNum);
	//부서에 따른 병명리스트 조회
	List<Map<String, Object>> diseaseListByDeptNum(Long deptNum) throws Exception;
	//약품리스트 조회
	List<Medicine> medicineList(String medSearchType, String medSearchKeyword);
	//즐겨찾기 약품리스트 조회
	List<Map<String, Object>> favMedicineList(Long docNum);
	//즐겨찾기 약품 등록
	Boolean addFavMedicine(Long docNum, String medicineNum);
	//외래진료 완료
	Boolean submitDiagnosis(DocDiagnosisDto docDiagDto);
}
