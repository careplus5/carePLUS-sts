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
    public Boolean savePrescriptionDiary(Prescription prescription) {
    
       
       if(!prescription.getMedicineNum().contains(",")) {
    	   PrescriptionDiary diary = new PrescriptionDiary();
           diary.setPrescriptionNum(prescription.getPrescriptionNum());
           diary.setPatNum(prescription.getPatNum());
           diary.setMedicineNum(prescription.getMedicineNum());
            diaryRepository.save(diary);
       } else {
    	 
         // medicineNum을 ','로 분리하여 각각의 PrescriptionDiary 생성
            String[] medicineNums = prescription.getMedicineNum().split(",");
            for (String medicineNum : medicineNums) {
                PrescriptionDiary diary = new PrescriptionDiary();
                diary.setPrescriptionNum(prescription.getPrescriptionNum());
                diary.setPatNum(prescription.getPatNum());
                diary.setMedicineNum(medicineNum);
                 diaryRepository.save(diary);}}
       
        return true;
    }

}
