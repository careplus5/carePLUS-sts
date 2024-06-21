package com.kosta.care.dto;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import com.kosta.care.entity.Prescription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrescriptionDto {
	private Long prescriptionNum;
	private String medicineNum;
	private Long patNum;
	private Long docNum;
	private String preDosage;
	private String preDosageTimes;
	private String preDosageTotal;
	private String preHowTake;
	private Date prescriptionDate;
	
	public Prescription toPrescription() {
		return Prescription.builder().
						prescriptionNum(prescriptionNum)
						.medicineNum(medicineNum)
						.patNum(patNum)
						.docNum(docNum)
						.prescriptionDosage(preDosage)
						.prescriptionDosageTimes(preDosageTimes)
						.prescriptionDosageTotal(preDosageTotal)
						.prescriptionHowTake(preHowTake)
						.prescriptionDate(prescriptionDate)
						.build();
	}
}
