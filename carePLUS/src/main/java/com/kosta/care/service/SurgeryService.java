package com.kosta.care.service;

import java.util.List;
import java.util.Map;

import com.kosta.care.dto.SurRecordDto;

public interface SurgeryService {
	//수술환자목록
	List<Map<String, Object>> surgeryListByDocNum(Long docNum);
	//수술환자정보, 수술정보
	Map<String, Object> surgeryInfoBySurgeryNum(Long surgeryNum) throws Exception;
	//수술참여 간호사 목록
	List<Map<String, Object>> surNurseListBySurgeryNum(Long surgeryNum);
	//수술기록 업데이트
	Boolean submitSurRecord(SurRecordDto surRecordDto);
}
