package com.kosta.care.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
public class Doctor implements Employee{
	@Id
	private Long docNum;
	@Column
	private Long profNum;
	@Column
	private Long departmentNum;
	@Column
	private String departmentName;
	@Column
	private Long jobNum;
	@Column
	private String docName;
	@Column
	private String docPassword;
	@Column
	private String docTel;
	@Column
	private String docPosition;
	@Column
	private String docEmail;
	@Column
	@ColumnDefault("true")
	private Boolean isNoticeAlaramOk;
	@Column
	@ColumnDefault("true")
	private Boolean isBookAlaramOk;
	@Column
	private String fcmToken ;

	public EmployeeDto DocToEmployeeDto() {
		 EmployeeDto emp = EmployeeDto.builder()
				.empNum(docNum)
				.profNum(profNum)
				.departmentNum(departmentNum)
				.jobNum(jobNum)
				.empName(docName)
				.empPassword(docPassword)
				.empTel(docTel)
				.empPosition(docPosition)
				.empEmail(docEmail)
				.build();
		 return emp;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return docNum;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return docName;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return docPassword;
	}

	@Override
	public void setPassword(String encodePassword) {
		this.docPassword=encodePassword;
	}

}
