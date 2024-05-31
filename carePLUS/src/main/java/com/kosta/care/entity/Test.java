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
public class Test {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer testNum;
	@Column
	private Integer docDiagnosisNum;
	@Column
	private Integer metNum;
	@Column
	private Integer patNum;
	@Column
	private Integer testRequestNum;
	@Column
	private Integer docNum;
	@Column
	private Integer testFileNum;
	@Column
	private String testName;
	@Column
	private String testPart;
	@Column
	private String testResult;
	@Column
	private Date testDate;
	@Column
	private String testNotice;
	@Column
	private String testState;
	@Column
	private Date testResvDate;
	@Column
	private String testOutInspectRecorde;
	@Column
	private Date testResvDateTime;
	
}
