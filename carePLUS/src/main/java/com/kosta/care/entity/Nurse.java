package com.kosta.care.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Nurse {
	@Id
	private Long nurId;
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
	private String nurDepartment2;
	@Column(columnDefinition = "boolean default true")
	private Boolean isNoticeAlaramOk;
	@Column(columnDefinition = "boolean default true")
	private Boolean isBookAlaramOk;
}
