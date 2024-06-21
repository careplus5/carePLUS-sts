package com.kosta.care.dto;

import java.sql.Date;

import com.kosta.care.entity.PrescriptionDiary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrescriptionDiaryDto {
	
	private Long prescriptionDiaryNum;
	private Long prescriptionNum;
	private Long patNum;
	private String medicineNum;
	private Long nurNum;
	private Long docNum;
	private Date prescriptionDiaryDate;
	private String prescriptionDiaryFre1;
	private String prescriptionDiaryFre2;
	private String prescriptionDiaryFre3;
	
	public PrescriptionDiary toPrescriptionDiary() {
		return PrescriptionDiary.builder().
				prescriptionDiaryNum(prescriptionDiaryNum)
						.prescriptionNum(prescriptionNum)
						.patNum(patNum)
						.docNum(docNum)
						.medicineNum(medicineNum)
						.prescriptionDiaryDate(prescriptionDiaryDate)
						.prescriptionDiaryFre1(prescriptionDiaryFre1)
						.prescriptionDiaryFre2(prescriptionDiaryFre2)
						.prescriptionDiaryFre3(prescriptionDiaryFre3)
						.build();
	}

}
