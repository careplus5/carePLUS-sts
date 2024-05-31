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
public class DocSchedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer docScheduleNum;
	@Column
	private Integer docNum;
	@Column
	private Integer jobNum;
	@Column
	private String docScheduleType;
	@Column
	private String docScheduleTitle;
	@Column
	private String docScheduleContent;
	@Column
	private Date docScheduleStartDate;
	@Column
	private Date docScheduleStartTime;
	@Column
	private Date docScheduleEndDate;
}