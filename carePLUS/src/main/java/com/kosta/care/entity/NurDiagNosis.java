package com.kosta.care.entity;

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
public class NurDiagNosis {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long nurDiagNum;
	@Column
	private Long preNum;
	@Column
	private Long nurNum;
	@Column
	private Long patNum;
	@Column
	private Long jobNum;
	@Column
	private String nurDiagContent;
	
	
	
	
}
