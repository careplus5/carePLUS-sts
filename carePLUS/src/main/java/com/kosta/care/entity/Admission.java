package com.kosta.care.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class Admission {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long admissionNum;
	@Column
	private Long docNum;
	@Column
	private Long jobNum;
	@Column
	private Long bedsNum;
	@Column
	private Long patNum;
	@Column
	private Long nurNum;
	@Column //입원일
	private Date admissionDate;
	@Column //입원예정일
	private Date admissionDueDate;
	@Column //퇴원예정일
	private Date admissionDischargeDueDate;
	@Column //퇴원일
	private Date admissionDischargeDate;
	@Column //입원사유
	private String admissionReason;
	@Column //인수인계
	private String admissionHandover;
	@Column //의사의 소견
	private String admissionDoctorOpinion;
	@Column //간호사의 퇴원 사유
	private String admissionDischargeOpinion;
	@Column //입원 상태
	private String admissionStatus;
	@Column //?
	private String admissionType;
	@Column //입원요청번호
	private Long admissionRequestNum;
	@Column //진료상태
	private String admissionDiagState;
	
	
}
