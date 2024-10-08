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
public class OperationUseCheck {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long num;
	
	@Column
	private Long surgeryNum;
	
	@Column
	private Date useDate;
	
	@Column
	private String time; //AM,PM
}
