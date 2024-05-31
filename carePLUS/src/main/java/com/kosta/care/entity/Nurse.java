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
	private Integer nurId;
	@Column
	private Integer profNum;
	@Column
	private Integer departmentNum;
	@Column
	private Integer jobNum;
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
