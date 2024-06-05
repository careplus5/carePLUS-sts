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
public class Admission {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long admNum;
	@Column
	private Long docNum;
	@Column
	private Long bedsNum;
	@Column
	private Long patNum;
	@Column
	private Long jobNum;
	@Column
	private Date admissionDate;
	@Column
	private Date admissionDueDate;
	@Column
	private Date admissionDcDueDate;
	@Column
	private Date admissionDc;
	@Column
	private String admissionReason;
	@Column
	private String admissionHandover;
	@Column
	private String admissionDoctorOpinion;
	@Column
	private String admissionState;
	@Column
	private String admissionType;
	
	
}
