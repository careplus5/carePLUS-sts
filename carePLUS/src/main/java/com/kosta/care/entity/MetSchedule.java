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
public class MetSchedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long metScheduleNum;
	@Column
	private Long metNum;
	@Column
	private Long jobNum;
	@Column
	private String metScheduleType;
	@Column
	private String metScheduleTitle;
	@Column
	private String metScheduleContent;
	@Column
	private Date metScheduleStartDate;
	@Column
	private Date metScheduleStartTime;
	@Column
	private Date metScheduleEndDate;
	@Column
	private Date metScheduleEndTime;
}