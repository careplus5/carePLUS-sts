package com.kosta.care.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.kosta.care.dto.DiagnosisDueDto;
import com.kosta.care.dto.DocDiagnosisDto;
import com.kosta.care.dto.PatientDto;
import com.kosta.care.entity.DiagnosisDue;
import com.kosta.care.entity.Medicine;

public interface DiagnosisDueService {
	
	//외래진료-대기환자목록 조회
	List<Map<String, Object>> diagDueListByDocNum(Long docNum);
	
	//외래진료-환자접수정보 조회
	Map<String, Object> diagDueInfoByDocDiagNum(Long docDiagNum);
	
	//외래진료-환자상태 업데이트
	void updateDocDiagnosisState(Long docDiagNum, String newState);
	
	//외래진료-부서에 따른 병명리스트 조회
	List<Map<String, Object>> diseaseListByDeptNum(Long deptNum) throws Exception;
	
	//외래진료-약품리스트 조회
	List<Medicine> medicineList(String medSearchType, String medSearchKeyword);
	
	//즐겨찾기 약품리스트 조회
	List<Map<String, Object>> favMedicineList(Long docNum);
	
	//즐겨찾기 약품 등록
	Boolean addFavMedicine(Long docNum, String medicineNum);
	
	//외래진료-진료완료
	Boolean submitDiagnosis(DocDiagnosisDto docDiagDto);
	
	//담당환자-담당환자 조회 (의사)
	List<Map<String, Object>> docPatListByDocNum(Long docNum, String searchType, String searchKeyword);
	
	//담당환자,외래진료-환자 이전진료내역 조회, 검색
	List<Map<String, Object>> patDiagListByPatNum(Long patNum, String searchType, String searchKeyword);
	
	// 전체 진료예약 조회
	List<DiagnosisDue> diagSearchAll();
	
	// 진료예약 등록( 환자등록 및 의사진단 )
	void diagnosisRegister(DiagnosisDueDto diagnosisDueDto) throws Exception;
	
	Map<String, Object> doctorDiagnosisDueList(Long departmentNum,Date date) throws Exception;

}
