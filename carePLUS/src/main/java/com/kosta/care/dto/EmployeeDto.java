package com.kosta.care.dto;

import com.kosta.care.entity.AdminHospital;
import com.kosta.care.entity.Doctor;
import com.kosta.care.entity.MedicalTechnician;
import com.kosta.care.entity.Nurse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDto {
	private Long empNum;
	private Long profNum;
	private Long departmentNum;
	private String departmentName;
	private Long jobNum;
	private String jobName;
	private	String department2Name;
	private String empPosition;
	private String empName;
	private String empTel;
	private String empEmail;
	private String empPassword;
	private Boolean isDiagnosAlarmOk;
	private Boolean isNoticeAlarmOk;
	private Boolean isSurgeryAlarmOk;
	private Boolean isPrescriptionAlarmOk;
	private Boolean isTestAlarmOk;
	private Boolean isDischargeAlarmOk;
	private Boolean isRequestAlarmOk;
	private Boolean isAdmissionAlarmOk;

}
