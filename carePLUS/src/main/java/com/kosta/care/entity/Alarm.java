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
public class Alarm {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer alarmNum;
	@Column
	private Integer empNum;
	@Column
	private String alarmTitle;
	@Column
	private Date alarmDate;
	@Column
	private Long alarmToken;
	@Column
	private Boolean alarmCheck;
	@Column
	private Boolean alarmDelivery;
}
