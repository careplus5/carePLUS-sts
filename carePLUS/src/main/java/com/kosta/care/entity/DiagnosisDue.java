package com.kosta.care.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.kosta.care.dto.DiagnosisDueDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiagnosisDue {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long diagnosisDueNum;  // 진료예약 번호 
	@Column 
	private Long patNum;  // 환자 번호 
	@Column
	private Long docNum;  // 의사 번호 
	@Column
	private String diagnosisDueState;  // 증상 
	@Column
	private String diagnosisDueEtc;  // 특이사항 
	@Column
	private Date diagnosisDueDate;  // 진료 예약일 
	@Column
	private Date diagnosisDueTime;  // 진료 시간 
	
	public DiagnosisDueDto toDiagnosisDueDto() {
		return new DiagnosisDueDto(diagnosisDueNum, patNum, docNum, diagnosisDueState, diagnosisDueEtc, diagnosisDueDate, diagnosisDueTime);
	}
}
