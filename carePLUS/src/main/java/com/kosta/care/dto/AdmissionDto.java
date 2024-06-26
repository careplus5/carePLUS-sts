package com.kosta.care.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdmissionDto {

	private Long admissionNum;
	
	private Long docNum;
	
	private Long jobNum;
	
	private Long bedsNum;
	
	private Long patNum;
	
	private String patName; // 필요해서 추가함 (김동현)
	
	private Long nurNum;
	
	private Date admissionDate;  // 입원일 
	
	private Date admissionDueDate;  // 입원예정일 
	
	private Date admissionDischargeDueDate;  // 퇴원예정일 
	
	private Date admissionDischargeDate;  // 퇴원일 
	
	private String admissionReason;  // 입원사유 
	
	private String admissionHandover;  // 인수인
	
	private String admissionDoctorOpinion;  // 의사의 소견 
	
	private String admissionDischargeOpinion;  // 간호사의 퇴원사유 
	
	private String admissionStatus;  // 입원상태 
	
	private String admissionType;
	
	private Long admissionRequestNum;  // 입원번호요청 
	
	private String admissionDiagState;  // 진료상태 
	
	//* 추가 
	private String docName; // 의사이름
}
