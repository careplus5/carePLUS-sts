package com.kosta.care.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;

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
	private Long alarmNum;
	@Column
	private Long empNum;
	@Column
	private String alarmCategory;
	@Column
	private String alarmContent;
	@Column
	private Date alarmDate;
	@Column
	@ColumnDefault("false")
	private Boolean alarmCheck;
	@Column
	@ColumnDefault("false")
	private Boolean alarmDelivery;
}
