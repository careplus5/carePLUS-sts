package com.kosta.care.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocDiagnosisNum {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer docDiagnosisNum;
	@Column
	private Integer prescriptionNum;
	@Column
	private Integer diseaseNum;
	@Column
	private Integer docNum;
	@Column
	private Integer patNum;
	@Column
	private String docDiagnosisContent;
	@Column
	private String docDiagnosisAdd;
	@Column
	private Integer testRequestNum;
	@Column
	private String docDiagnosisOrder;
	@Column
	private String docDiagnosisState;
	@Column
	@CreationTimestamp
	private Date docDiagnosisDate;
	@Column
	private String docDiagnosisKind;
}
