package com.kosta.care.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import com.kosta.care.dto.EmployeeDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
public class Nurse implements Employee {
	@Id
	private Long nurNum;
	@Column
	private Long profNum;
	@Column
	private Long departmentNum;
	@Column
	private String departmentName;
	@Column
	private Long jobNum;
	@Column
	private String nurName;
	@Column
	private String nurPassword;
	@Column
	private String nurTel;
	@Column
	private String nurPosition;
	@Column
	private String nurEmail;
	@Column
	private String nurDepartment2Name;
	@ColumnDefault("true")
	private Boolean isNoticeAlarmOk;
	@Column
	@ColumnDefault("true")
	private Boolean isSurgeryAlarmOk;
	@Column
	@ColumnDefault("true")
	private Boolean isAdmissionAlarmOk;
	@Column
	@ColumnDefault("true")
	private Boolean isRequestAlarmOk;	
	@Column
	private String fcmToken ;
	
	public EmployeeDto NurToEmployeeDto() {
		 EmployeeDto emp = EmployeeDto.builder()
					.empNum(nurNum)
					.profNum(profNum)
					.departmentNum(departmentNum)
					.departmentName(departmentName)
					.department2Name(nurDepartment2Name)
					.jobNum(jobNum)
					.empName(nurName)
					.empPassword(nurPassword)
					.empTel(nurTel)
					.empPosition(nurPosition)
					.empEmail(nurEmail)
					.isNoticeAlarmOk(isNoticeAlarmOk)
					.isSurgeryAlarmOk(isSurgeryAlarmOk)
					.isAdmissionAlarmOk(isAdmissionAlarmOk)
					.isRequestAlarmOk(isRequestAlarmOk)
					.build();
			 return emp;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return nurNum;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return nurName;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return nurPassword;
	}

	@Override
	public void setPassword(String encodePassword) {
		this.nurPassword=encodePassword;
	}

}
