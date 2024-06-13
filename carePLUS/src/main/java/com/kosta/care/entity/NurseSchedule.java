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
	public Long getNurScheduleNum() {
		return nurScheduleNum;
	}
	public void setNurScheduleNum(Long nurScheduleNum) {
		this.nurScheduleNum = nurScheduleNum;
	}
	public Long getNurNum() {
		return nurNum;
	}
	public void setNurNum(Long nurNum) {
		this.nurNum = nurNum;
	}
	public Long getJobNum() {
		return jobNum;
	}
	public void setJobNum(Long jobNum) {
		this.jobNum = jobNum;
	}
	public String getNurScheduleType() {
		return nurScheduleType;
	}
	public void setNurScheduleType(String nurScheduleType) {
		this.nurScheduleType = nurScheduleType;
	}
	public String getNurScheduleTitle() {
		return nurScheduleTitle;
	}
	public void setNurScheduleTitle(String nurScheduleTitle) {
		this.nurScheduleTitle = nurScheduleTitle;
	}
	public String getNurScheduleContent() {
		return nurScheduleContent;
	}
	public void setNurScheduleContent(String nurScheduleContent) {
		this.nurScheduleContent = nurScheduleContent;
	}
	public Date getNurScheduleStartDate() {
		return nurScheduleStartDate;
	}
	public void setNurScheduleStartDate(Date nurScheduleStartDate) {
		this.nurScheduleStartDate = nurScheduleStartDate;
	}
	public Date getNurScheduleStartTime() {
		return nurScheduleStartTime;
	}
	public void setNurScheduleStartTime(Date nurScheduleStartTime) {
		this.nurScheduleStartTime = nurScheduleStartTime;
	}
	public Date getNurScheduleEndDate() {
		return nurScheduleEndDate;
	}
	public void setNurScheduleEndDate(Date nurScheduleEndDate) {
		this.nurScheduleEndDate = nurScheduleEndDate;
	}
	public Date getNurScheduleEndTime() {
		return nurScheduleEndTime;
	}
	public void setNurScheduleEndTime(Date nurScheduleEndTime) {
		this.nurScheduleEndTime = nurScheduleEndTime;
	}
	
	
}
