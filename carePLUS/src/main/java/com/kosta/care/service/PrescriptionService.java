package com.kosta.care.service;

import java.util.List;
import java.util.Optional;
import com.kosta.care.entity.Prescription;

public interface PrescriptionService {
	
	public Boolean savePrescriptionDiary(Prescription prescription);
	
	// 환자 번호로 해당 환자 처방 리스트 출력
	List<Prescription> patientPrescriptionList(Long patNum) throws Exception;
	
	Optional<Prescription> patientPrescriptionView(Long prescriptionNum) throws Exception;
}
