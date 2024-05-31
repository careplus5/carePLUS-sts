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
public class AdminHospital {
	@Id
	private Integer admNum;
	@Column
	private Integer profNum;
	@Column
	private Integer departmentNum;
	@Column
	private Integer jobNum;
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
	private String admPlace;
	@Column(columnDefinition = "boolean default true")
	private Boolean isNoticeAlaramOk;
	@Column(columnDefinition = "boolean default true")
	private Boolean isBookAlaramOk;
	
}
