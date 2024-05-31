package com.kosta.care.dto;

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
	private String patDetailAddress;
	private String patTel1;
	private String patTel2;
	private String petTel3;
	private String petTel;
	private String patHeight;
	private String patWeight;
	private String patBloodType;
	private String patHistory;
}
