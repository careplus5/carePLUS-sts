//package com.kosta.care.service;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.kosta.care.dto.PrescriptionDto;
//import com.kosta.care.entity.Prescription;
//import com.kosta.care.repository.PrescriptionRepository;
//
//@Service
//public class PrescriptionServiceIpl implements PrescriptionService {
//
//	@Autowired
//	private PrescriptionRepository prescriptionRepository;
//	
//	// 환자 번호로 처방 리스트 출력
//	@Override
//	public List<Prescription> patientPrescriptionList(Long patNum) throws Exception {
//		return prescriptionRepository.findByPatNum(patNum);
//	}
//
//	@Override
//	public Optional<Prescription> patientPrescriptionView(Long prescriptionNum) throws Exception {
//		return prescriptionRepository.findById(prescriptionNum);
//	}
//
//	@Override
//	public Boolean savePrescriptionDiary(Prescription prescription) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	
//	
//}
