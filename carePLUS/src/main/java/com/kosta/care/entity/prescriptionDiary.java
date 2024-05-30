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
	private Integer diaryNum;
	@Column
	private Integer prescriptionNum;
	@Column
	private Integer patNum;
	@Column
	private Integer nurNum;
	@Column
	private Integer docNum;
	@Column
	private Integer jobNum;
	@Column
	private String prescriptionDiarySort;
	@Column
	private String prescriptionDiaryDiv;  
	@Column
	private String prescriptionDiaryName;
	@Column
	@CreationTimestamp
	private Date  prescriptionDiaryDate;
	@Column
	private Integer prescriptionDiaryQuantity;
	@Column
	private Integer prescriptionDiaryFrequency;
	
	

	
}
