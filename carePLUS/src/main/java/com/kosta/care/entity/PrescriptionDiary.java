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

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionDiary {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long prescriptionDiaryNum;
	@Column
	private Long prescriptionNum;
	@Column
	private Long patNum;
	@Column
	private String medicineNum;
	@Column
	private Long nurNum;
	@Column//내일, 모레꺼도 필요할지도 모르잖아..? 그래서 일단 curdate()같은거안했는데 service에서 하세요
	private Long docNum;
	@Column
	private Date prescriptionDiaryDate;
	@Column
	private String prescriptionDiaryFre1;
	@Column
	private String prescriptionDiaryFre2;
	@Column
	private String prescriptionDiaryFre3;
	

	
}
