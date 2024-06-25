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
public class SurgeryRequestDto {
	private Long surgeryRequestNum;
	private Long patNum;
	private String surPeriod;
	private String surReason;
	private Date surDate;
	private Long departmentNum;
	private String departmentName;
	private Long docNum;
	private String docName;
}
