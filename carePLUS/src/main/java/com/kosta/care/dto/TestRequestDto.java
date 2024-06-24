package com.kosta.care.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestRequestDto {
	
	private Long testRequestNum;
	private Long patNum;
	private String patName;
	private String patJumin;
	private String patGender;
	private Long docNum;
	private String docName;
	private String patBloodType;
	private String testName;  // 검사 종류 (CT, MRI, X-Ray) 
	private String testRequestAcpt;  // 검사 승인 여부
	private String testPart;  // 검사 부위
	private String departmentName; //요청과명
	private Long docDiagnosisNum; //진료번호	
}
