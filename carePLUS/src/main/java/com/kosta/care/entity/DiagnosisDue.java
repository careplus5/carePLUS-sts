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
	private Long diagnosisDueNum;
	@Column 
	private Long patNum;
	@Column
	private Long docNum;
	@Column
	private String diagnosisDueState;
	@Column
	private String diagnosisDueEtc;
	@Column
	private Date diagnosisDueDate;
	@Column
	private Date diagnosisDueTime;
	
	public DiagnosisDueDto toDiagnosisDueDto() {
		return new DiagnosisDueDto(diagnosisDueNum, patNum, docNum, diagnosisDueState, diagnosisDueEtc, diagnosisDueDate, diagnosisDueTime);
	}
}
