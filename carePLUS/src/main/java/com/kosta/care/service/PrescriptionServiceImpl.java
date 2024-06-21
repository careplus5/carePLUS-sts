package com.kosta.care.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.care.entity.Prescription;
import com.kosta.care.entity.PrescriptionDiary;
import com.kosta.care.repository.PrescriptionDiaryRepository;
import com.kosta.care.repository.PrescriptionRepository;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {
	
	@Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private PrescriptionDiaryRepository diaryRepository;

	@Override
	public Prescription savePrescription(Prescription prescription) {
		   Prescription savedPrescription = prescriptionRepository.save(prescription);

	        // medicineNum을 ','로 분리하여 각각의 PrescriptionDiary 생성
	        String[] medicineNums = savedPrescription.getMedicineNum().split(",");
	        for (String medicineNum : medicineNums) {
	            PrescriptionDiary prescriptionDiary = PrescriptionDiary.builder()
	                    .prescriptionNum(savedPrescription.getPrescriptionNum())
	                    .patNum(savedPrescription.getPatNum())
	                    .nurNum(null) // 필요한 경우 적절히 설정
	                    .prescriptionDiaryDate(null) // 필요한 경우 적절히 설정
	                    .medicineNum(medicineNum)
	                    .build();
	            diaryRepository.save(prescriptionDiary);
	        }

	        return savedPrescription;
	}

}
