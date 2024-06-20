package com.kosta.care.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SurgeryRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long surgeryRequestNum;
	@Column
	private Long patNum;
	@Column
	private Long docNum;
	@Column//Long같기도 한데 그냥 String으로 할게.. 바꾸거나 parseInt.해줘...ㅠ
	private String surPeriod;
	@Column
	private String surReason;
	@Column
	private Date surDate;
	@Column
	private Long departmentNum;
	@Column
	private String surgeryRequestAcpt;
}
