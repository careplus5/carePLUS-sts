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
public class Patient {
	@Id
	private Integer patNum;
	@Column
	private String patName;
	@Column
	private String patJumin;
	@Column
	private String patGender;
	@Column
	private String patAddress;
	@Column
	private String patDetailAddress;
	@Column
	private String patTel1;
	@Column
	private String patTel2;
	@Column
	private String petTel3;
	@Column
	private String petTel;
	@Column
	private String patHeight;
	@Column
	private String patWeight;
	@Column
	private String patBloodType;
	@Column
	private String patHistory;
}
