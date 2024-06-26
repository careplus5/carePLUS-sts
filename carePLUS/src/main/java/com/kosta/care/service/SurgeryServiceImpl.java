package com.kosta.care.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosta.care.dto.SurRecordDto;
import com.kosta.care.entity.Doctor;
import com.kosta.care.entity.Patient;
import com.kosta.care.entity.Surgery;
import com.kosta.care.entity.SurgeryRequest;
import com.kosta.care.repository.SurgeryDslRespository;
import com.kosta.care.repository.SurgeryRepository;
import com.querydsl.core.Tuple;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SurgeryServiceImpl implements SurgeryService {

	private final SurgeryDslRespository surgeryDslRespository;
	private final SurgeryRepository surgeryRepository;
	
	private final ObjectMapper objectMapper;
	
	@Override
	public List<Map<String, Object>> surgeryListByDocNum(Long docNum) {
		List<Tuple> tuples = surgeryDslRespository.findSurgeryListByDocNum(docNum);
		List<Map<String, Object>> surgeryList = new ArrayList<>();
		
		for(Tuple tuple : tuples) {
			Surgery surgery = tuple.get(0, Surgery.class);
			Patient patient = tuple.get(1, Patient.class);
			
			Map<String, Object> map = objectMapper.convertValue(surgery, Map.class);
			map.put("patNum", patient.getPatNum());
			map.put("patName", patient.getPatName());
			surgeryList.add(map);
		}
		
		if(surgeryList.isEmpty()) {
			return null;
		}
		
		return surgeryList;
	}

	@Override
	public Map<String, Object> surgeryInfoBySurgeryNum(Long surgeryNum) throws Exception {
		Tuple tuple = surgeryDslRespository.findSurgeryInfoBySurgeryNum(surgeryNum);
		
		Surgery surgery = tuple.get(0, Surgery.class);
		Patient patient = tuple.get(1, Patient.class);
		SurgeryRequest surgeryRequest = tuple.get(2, SurgeryRequest.class);
		Doctor doctor = tuple.get(3, Doctor.class);
		
		Optional<Surgery> osurgeryNum = surgeryRepository.findById(surgeryNum);
		
		if(osurgeryNum.isEmpty()) throw new Exception("수술정보 없음");
		
		String newState = "ing";
		surgery.setSurgeryState(newState);
		
		Map<String, Object> map = objectMapper.convertValue(surgery, Map.class);
		map.put("patNum", patient.getPatNum());
		map.put("patName", patient.getPatName());
		map.put("patJumin", patient.getPatJumin());
		map.put("patBloodType", patient.getPatBloodType());
		map.put("patGender", patient.getPatGender());
		map.put("surPeriod", surgeryRequest.getSurPeriod());
		map.put("surReason", surgeryRequest.getSurReason());
		map.put("docName", doctor.getDocName());
		
		//수술환자 상태 업데이트
		surgeryRepository.save(surgery);
		
		return map;
	}

	@Override
	public List<Map<String, Object>> surNurseListBySurgeryNum(Long surgeryNum) {
		List<Tuple> tuples = surgeryDslRespository.findSurNurseListBySurgeryNum(surgeryNum);
		List<Map<String, Object>> surNurseList = new ArrayList<>();
		
		for(Tuple tuple : tuples) {
			Surgery surgery = tuple.get(0, Surgery.class);
			Long nurNum = tuple.get(1, Long.class);
			String nurName = tuple.get(2, String.class);
			String deptName = tuple.get(2, String.class);
			
			Map<String, Object> map = objectMapper.convertValue(surgery, Map.class);
			map.put("nurNum", nurNum);
			map.put("nurName", nurName);
			map.put("deptName", deptName);
			surNurseList.add(map);
		}
		
		if(surNurseList.isEmpty()) return null;
		
		return surNurseList;
	}

	@Override
	public Boolean submitSurRecord(SurRecordDto surRecordDto) {
		Optional<Surgery> oSurgery = surgeryRepository.findById(surRecordDto.getSurgeryNum());
		
		if(oSurgery.isEmpty()) {
			return false;
		}
		
		Surgery surgery = oSurgery.get();
		
		surgery.setSurgeryAnesthesia(surRecordDto.getSurAnesthesia());
		surgery.setSurgeryAnesthesiaPart(surRecordDto.getSurAnesthesiaPart());
		surgery.setSurgeryBloodPack(surRecordDto.getSurBloodPack());
		surgery.setSurgeryBloodPackCnt(surRecordDto.getSurBloodPackCnt());
		surgery.setSurgeryStartTime(surRecordDto.getSurStartTime());
		surgery.setSurgeryDueEnd(surRecordDto.getSurEndTime());
		surgery.setSurgeryTotalTime(surRecordDto.getSurTotalTime());
		surgery.setSurgeryResult(surRecordDto.getSurResult());
		surgery.setSurgeryEtc(surRecordDto.getSurEtc());
		surgery.setSurgeryState("end");
		
		Surgery updateSurRecord = surgeryRepository.save(surgery);
		
		return updateSurRecord != null;
	}

}
