package com.kosta.care.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class prescriptionDiary extends Prescription{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer prescriptionDiaryNum;
	@Column
	private Integer patNum;
	@Column
	private Integer nurNum;
	@Column//내일, 모레꺼도 필요할지도 모르잖아..? 그래서 일단 curdate()같은거안했는데 service에서 하세요
	private Date prescriptionDiaryDate;
	@Column
	private String prescriptionDiaryFre1;
	@Column
	private String prescriptionDiaryFre2;
	@Column
	private String prescriptionDiaryFre3;
	
	

	
}
