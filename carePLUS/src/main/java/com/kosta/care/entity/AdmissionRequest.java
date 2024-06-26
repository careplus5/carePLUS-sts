package com.kosta.care.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdmissionRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long admissionRequestNum;
	@Column
	private Long patNum;
	@Column
	private Long diagnosisNum;
	@Column
	private Long docNum;
	@Column
	private Long jobNum;
	@Column
	private Long departmentNum;
	@Column
	private Long admissionRequestPeriod;
	@Column
	private String admissionRequestReason;
	@Column
	private String admissionRequestAcpt;
	
}
