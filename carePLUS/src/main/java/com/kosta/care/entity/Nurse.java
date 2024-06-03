package com.kosta.care.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class Nurse {
	@Id
	private Long nurNum;
	@Column
	private Long profNum;
	@Column
	private Long departmentNum;
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
	private String nurDepartment1;
	@Column
	private String nurDepartment2Name;
	@Column
	@ColumnDefault("true")
	private Boolean isNoticeAlaramOk;
	@Column
	@ColumnDefault("true")
	private Boolean isBookAlaramOk;
	
	public EmployeeDto NurToEmployeeDto() {
		 EmployeeDto emp = EmployeeDto.builder()
					.empNum(nurNum)
					.profNum(profNum)
					.departmentNum(departmentNum)
					.department2Name(nurDepartment2Name)
					.jobNum(jobNum)
					.empName(nurName)
					.empPassword(nurPassword)
					.empTel(nurTel)
					.empPosition(nurPosition)
					.empEmail(nurEmail)
					.build();
			 return emp;
	}

}
