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
public class DiagnosisDueDto {
	private Long diagnosisDueNum;
	private Long patNum;
	private Long docNum;
	private String diagnosisDueState;
	private String diagnosisDueEtc;
	private Date diagnosisDueDate;
	private Date diagnosisDueTime;
}
