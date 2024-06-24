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
public class AdminHospital implements Employee {
	@Id
	private Long admNum;
	@Column
	private Long profNum;
	@Column
	private Long departmentNum;
	@Column
	private String departmentName;
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
    private Boolean isNoticeAlarmOk;
    @Column
    @ColumnDefault("true")
    private Boolean isPrescriptionAlarmOk;
    @Column
    @ColumnDefault("true")
    private Boolean isDischargeAlarmOk;
	@Column
	private String fcmToken ;
	
	public EmployeeDto AdmToEmployeeDto() {
		EmployeeDto emp = EmployeeDto.builder()
				.empNum(admNum)
				.profNum(profNum)
				.departmentNum(departmentNum)
				.departmentName(departmentName)
				.jobNum(jobNum)
				.empName(admName)
				.empPassword(admPassword)
				.empTel(admTel)
				.empPosition(admPosition)
				.empEmail(admEmail)
				.isNoticeAlarmOk(isNoticeAlarmOk)
				.isPrescriptionAlarmOk(isPrescriptionAlarmOk)
				.isDischargeAlarmOk(isDischargeAlarmOk)
				.build();
		 return emp;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return admNum;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return admName;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return admPassword;
	}

	@Override
	public void setPassword(String encodePassword) {
		this.admPassword=encodePassword;
		
	}
}
