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
public class MedicalTechnician implements Employee{
	@Id
	private Long metNum;
	@Column
	private Long profNum;
	@Column
	private Long departmentNum;
	@Column
	private String departmentName;
	@Column
	private Long jobNum;
	@Column
	private String metName;
	@Column
	private String metPassword;
	@Column
	private String metTel;
	@Column
	private String metJob;
	@Column
	private String metPosition;
	@Column
	private String metEmail;
	@Column
	private String metDepartment2Name;
	@Column
	@ColumnDefault("true")
	private Boolean isNoticeAlarmOk;
	@Column
	@ColumnDefault("true")
	private Boolean isTestAlarmOk;
	@Column
	private String fcmToken ;
	
	public EmployeeDto MetToEmployeeDto() {
		EmployeeDto emp = EmployeeDto.builder()
				.empNum(metNum)
				.profNum(profNum)
				.departmentNum(departmentNum)
				.departmentName(departmentName)
				.department2Name(metDepartment2Name)
				.jobNum(jobNum)
				.empName(metName)
				.empPassword(metPassword)
				.empTel(metTel)
				.empPosition(metPosition)
				.empEmail(metEmail)
				.isNoticeAlarmOk(isNoticeAlarmOk)
				.isTestAlarmOk(isTestAlarmOk)
				.build();
		 return emp;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return metNum;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return metName;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return metPassword;
	}

	@Override
	public void setPassword(String encodePassword) {
		this.metPassword=encodePassword;
		
	}

}
