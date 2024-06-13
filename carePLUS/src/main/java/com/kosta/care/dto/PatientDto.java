package com.kosta.care.dto;

import com.kosta.care.entity.Patient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientDto {
	private Long patNum;
	private String patName;
	private String patJumin;
	private String patGender;
	private String patAddress;
	private String patTel;
	private String patHeight;
	private String patWeight;
	private String patBloodType;
	private String patHistory;
	
	public Patient patientEntity() {
		return Patient.builder()
					.patNum(patNum)
					.patName(patName)
					.patJumin(patJumin)
					.patGender(patGender)
					.patAddress(patAddress)
					.patTel(patTel)
					.patHeight(patHeight)
					.patWeight(patWeight)
					.patBloodType(patBloodType)
					.patHistory(patHistory)
					.build();
		
	}
}
