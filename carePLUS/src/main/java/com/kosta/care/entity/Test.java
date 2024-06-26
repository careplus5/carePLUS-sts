package com.kosta.care.entity;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.kosta.care.dto.TestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Test{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long testNum;
	@Column
	private Long docDiagnosisNum;
	@Column
	private Long metNum;
	@Column
	private Long patNum;
	@Column
	private Long testRequestNum;
	@Column
	private Long docNum;
	@Column
	private Long testFileNum;
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
	private String testStatus;
	@Column
	private Date testAppointmentDate;
	@Column
	private String testOutInspectRecord;
	@Column
	private Time testAppointmentTime;


	
}
