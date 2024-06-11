package com.kosta.care.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long testRequestNum;  // 검사요청 고유 번호
	@Column
	private Long patNum;  // 환자 번호
	@Column
	private Long docNum;  // 의사 번호 
	@Column
	private String testName;  // 검사 종류 (ct, mri, x-ray) 
	@Column
	private String testRequestAcpt;  // 요청 수락 여부 
	@Column
	private String testPart;  // 검사 부위
	@Column
	private String testRequest;
}
