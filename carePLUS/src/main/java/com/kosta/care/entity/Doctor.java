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
public class Doctor {
	@Id
	private Long docNum;
	@Column
	private Long profNum;
	@Column
	private Long departmentId;
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
	@Column(columnDefinition = "boolean default true")
	private Boolean isNoticeAlaramOk;
	@Column(columnDefinition = "boolean default true")
	private Boolean isBookAlaramOk;

}
