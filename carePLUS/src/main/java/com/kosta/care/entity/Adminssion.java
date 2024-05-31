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
public class Adminssion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long admNum;
	@Column
	private Long docNum;
	@Column
	private Long bedsId;
	@Column
	private Long patNum;
	@Column
	private Long jobNum;
	@Column
	private Date adminssionDate;
	@Column
	private Date adminssionDueDate;
	@Column
	private Date adminssionDcDueDate;
	@Column
	private Date adminssionDc;
	@Column
	private String adminssionReason;
	@Column
	private String adminssionHandover;
	@Column
	private String adminssionDoctorOpinion;
	@Column
	private String adminssionState;
	@Column
	private String adminssionType;
	
	
}
