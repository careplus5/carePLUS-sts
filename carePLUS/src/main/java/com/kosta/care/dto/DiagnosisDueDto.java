package com.kosta.care.dto;

import java.sql.Date;

import com.kosta.care.entity.Doctor;
import com.kosta.care.entity.Patient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiagnosisDueDto {
	private Long diagnosisDueNum;  // 진료예약 번호 
	private Long patNum;  // 환자 번호 
	private Long docNum;  // 의사 번호 
	private String diagnosisDueState;  // 증상 
	private String diagnosisDueEtc;  // 특이 사항 
	private Date diagnosisDueDate;  // 진료 예약일 
	private Date diagnosisDueTime;  // 진료 시간 
	
	
}
