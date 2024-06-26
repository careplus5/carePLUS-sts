package com.kosta.care.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdmissionRequestDto {
	
	private Long admissionRequestNum;  //0
	private Long patNum;  // 0
	private String patName;  // 환자 이름 
	private String patJumin;  // 환자 주민등록번호 
	private String patGender;  // 환자 성별 
	private String patTel;  // 환자 연락처 
	private String patAddress;  // 환자 주소 
	private Long diagnosisNum;  //0
	private String departmentName;  // 부서 이름
	private Long docNum;  //0
	private String docName;  // 주치의 
	private Long jobNum;  // 0
	private Long admissionRequestPeriod;  // 입원기간0
	private String admissionRequestReason;  // 입원사유0
	private String admissionRequestAcpt;  //0
	private Long department; // 부서번호
    
	private Date admissionDate;
    private Date admissionDueDate;
    private Long bedsNum;
    private Long departmentNum;
    private Long bedsDept;
    private Long bedsWard;
    private Long bedsRoom;
    private Long bedsBed;
    private Date admissionDischargeDueDate;
	
	public AdmissionRequestDto(String departmentName, String docName, String admissionRequestReason, Long admissionRequestPeriod, Long department) {
		this.departmentName=departmentName;
		this.docName=docName;
		this.admissionRequestReason=admissionRequestReason;
		this.admissionRequestPeriod=admissionRequestPeriod;
		this.department=department;
	}
	
	
}
