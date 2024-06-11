package com.kosta.care.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdmissionRequestDto {
	
	private Long admissionRequestNum;
	private Long patNum;
	private String patName;
	private String patJumin;
	private String patGender;
	private String patTel;
	private String patAddress;
	private Long diagnosisNum;
	private Long docNum;
	private Long jobNum;
	private String admissionRequestPeriod;
	private String admissionRequestReason;
}
