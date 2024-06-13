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
public class AdmissionRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long admissionRecordNum;
	@Column
	private Long docDiagnosisNum;
	@Column
	private Long jobNum;
	@Column
	@CreationTimestamp
	private Date admissionRecordDate;
	@Column
	private String admissionRecordContent;
	@Column
	private Long admissionNum;
}
