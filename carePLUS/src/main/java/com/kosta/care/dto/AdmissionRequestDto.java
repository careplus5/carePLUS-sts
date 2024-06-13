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
	private String patName;  // 환자 이름 
	private String patJumin;  // 환자 주민등록번호 
	private String patGender;  // 환자 성별 
	private String patTel;  // 환자 연락처 
	private String patAddress;  // 환자 주소 
	private Long diagnosisNum;
	private String departmentName;  // 부서 이름
	private Long docNum;  
	private Long docName;  // 주치의 
	private Long jobNum;  // 
	private String admissionRequestPeriod;
	private String admissionRequestReason;
}
