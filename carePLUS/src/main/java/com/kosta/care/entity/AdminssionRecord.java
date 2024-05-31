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
public class AdminssionRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long adminssionRecordNum;
	@Column
	private Long docDiagnosisNum;
	@Column
	private Long jobNum;
	@Column
	@CreationTimestamp
	private Date adminssionRecordDate;
	@Column
	private String adminssionRecordContent;
}
