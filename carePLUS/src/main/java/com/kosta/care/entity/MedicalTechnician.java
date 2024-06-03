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
public class MedicalTechnician {
	@Id
	private Long metNum;
	@Column
	private Long profNum;
	@Column
	private Long departmentNum;
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
	private Boolean isNoticeAlaramOk;
	@Column
	@ColumnDefault("true")
	private Boolean isBookAlaramOk;
	
	public EmployeeDto MetToEmployeeDto() {
		EmployeeDto emp = EmployeeDto.builder()
				.empNum(metNum)
				.profNum(profNum)
				.departmentNum(departmentNum)
				.department2Name(metDepartment2Name)
				.jobNum(jobNum)
				.empName(metName)
				.empPassword(metPassword)
				.empTel(metTel)
				.empPosition(metPosition)
				.empEmail(metEmail)
				.build();
		 return emp;
	}

}
