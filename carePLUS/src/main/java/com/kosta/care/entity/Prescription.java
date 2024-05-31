package com.kosta.care.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Prescription {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer prescriptionNum;
	@Column
	private Integer medicineNum;
	@Column
	private Integer patNum;
	@Column
	private Integer docNum;
	@Column
	private Integer prescriptionDosage;
	@Column
	private Integer prescriptionDosageTimes;
	@Column
	private Integer prescriptionDosageTotal;
	@Column
	private String prescriptionHowTake;
	@Column
	@CreationTimestamp
	private Date prescriptionDate;
}
