package com.kosta.care.service;

import java.util.List;

import com.kosta.care.entity.PrescriptionDiary;

public interface PrescriptionDiaryService {
	
	public List<PrescriptionDiary> getPrescriptionDiariesByPrescriptionNum(Long prescriptionNum);

}
