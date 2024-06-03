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
public class AdminHospital {
	@Id
	private Long admNum;
	@Column
	private Long profNum;
	@Column
	private Long departmentNum;
	@Column
	private Long jobNum;
	@Column
	private String admName;
	@Column
	private String admPassword;
	@Column
	private String admTel;
	@Column
	private String admPosition;
	@Column
	private String admEmail;
	@Column
	@ColumnDefault("true")
	private Boolean isNoticeAlaramOk;
	@Column
	@ColumnDefault("true")
	private Boolean isBookAlaramOk;
	
	public EmployeeDto AdmToEmployeeDto() {
		EmployeeDto emp = EmployeeDto.builder()
				.empNum(admNum)
				.profNum(profNum)
				.departmentNum(departmentNum)
				.jobNum(jobNum)
				.empName(admName)
				.empPassword(admPassword)
				.empTel(admTel)
				.empPosition(admPosition)
				.empEmail(admEmail)
				.build();
		 return emp;
	}
}
