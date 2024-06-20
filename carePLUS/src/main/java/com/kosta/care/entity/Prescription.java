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
@Builder
public class Prescription {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long prescriptionNum;
	@Column
	private String medicineNum;
	@Column
	private Long patNum;
	@Column
	private Long docNum;
	@Column
	private Long prescriptionDosage;
	@Column
	private Long prescriptionDosageTimes;
	@Column
	private Long prescriptionDosageTotal;
	@Column
	private String prescriptionHowTake;
	@Column
	@CreationTimestamp
	private Date prescriptionDate;
}
