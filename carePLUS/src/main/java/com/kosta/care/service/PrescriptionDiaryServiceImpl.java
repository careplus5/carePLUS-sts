package com.kosta.care.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.care.entity.PrescriptionDiary;
import com.kosta.care.repository.PrescriptionDiaryRepository;
@Service
public class PrescriptionDiaryServiceImpl implements PrescriptionDiaryService {

	@Autowired
    private PrescriptionDiaryRepository prescriptionDiaryRepository;

    public List<PrescriptionDiary> getPrescriptionDiariesByPrescriptionNum(Long prescriptionNum) {
        return prescriptionDiaryRepository.findByPrescriptionNum(prescriptionNum);
    }

}
