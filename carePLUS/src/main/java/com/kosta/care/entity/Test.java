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
	@ManyToOne
    @JoinColumn(name = "patNum", referencedColumnName = "patNum", insertable = false, updatable = false)
	private Patient patient;
	@ManyToOne
    @JoinColumn(name = "testRequestNum", referencedColumnName = "testRequestNum", insertable = false, updatable = false)
	private TestRequest testRequest;
	@Column
	private Long docNum;
	@Column
	private Long testFileNum;
	@Column
	private String testName;
	@Column
	private Long testRequestNum;
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
	
//	@OneToMany(mappedBy="test", fetch=FetchType.LAZY)
//	private List<TestFile> testFileList = new ArrayList<>();
	
	 public TestDto toDto() {
	        return TestDto.builder()
	            .testNum(this.testNum)
	            .docDiagnosisNum(this.docDiagnosisNum)
	            .metNum(this.metNum)
	            .patNum(this.patient != null ? this.patient.getPatNum() : null)
	            .testRequestNum(this.testRequest != null ? this.testRequest.getTestRequestNum() : null)
	            .docNum(this.testRequest != null ? this.testRequest.getDocNum() : null)
	            .testName(this.testRequest != null ? this.testRequest.getTestName() : null)
	            .testPart(this.testRequest != null ? this.testRequest.getTestPart() : null)
	            .testResult(this.testResult)
	            .testDate(this.testDate)
	            .testNotice(this.testNotice)
	            .testStatus(this.testStatus)
	            .testAppointmentDate(this.testAppointmentDate)
	            .testOutInspectRecord(this.testOutInspectRecord)
	            .testAppointmentTime(this.testAppointmentTime)
	            .patName(this.patient != null ? this.patient.getPatName() : null)
	            .patGender(this.patient != null ? this.patient.getPatGender() : null)
	            .patJumin(this.patient != null ? this.patient.getPatJumin() : null)
	            .patBloodType(this.patient != null ? this.patient.getPatBloodType() : null)
	            .build();
	    }

	
}
