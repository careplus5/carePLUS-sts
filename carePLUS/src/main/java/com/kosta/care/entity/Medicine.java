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
public class Medicine {
	@Id
	private String medicineNum;
	@Column
	private String medicineEnName;
	@Column
	private String medicineKorName;
	@Column
	private String medicineStandard;
	@Column
	private String medicineIngCode;
	@Column
	private String medicineIngEnName;
	@Column
	private String medicineIngKorName;
}
