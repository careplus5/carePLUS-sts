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
public class NurseSchedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long nurScheduleNum;
	@Column
	private Long nurNum;
	@Column
	private Long jobNum;
	@Column
	private String nurScheduleType;
	@Column
	private String nurScheduleTitle;
	@Column
	private String nurScheduleContent;
	@Column
	private Date nurScheduleStartDate;
	@Column
	private Date nurScheduleStartTime;
	@Column
	private Date nurScheduleEndDate;
	@Column
	private Date nurScheduleEndTime;
}
