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
	
	public Long getMetScheduleNum() {
		return metScheduleNum;
	}
	public void setMetScheduleNum(Long metScheduleNum) {
		this.metScheduleNum = metScheduleNum;
	}
	public Long getMetNum() {
		return metNum;
	}
	public void setMetNum(Long metNum) {
		this.metNum = metNum;
	}
	public Long getJobNum() {
		return jobNum;
	}
	public void setJobNum(Long jobNum) {
		this.jobNum = jobNum;
	}
	public String getMetScheduleType() {
		return metScheduleType;
	}
	public void setMetScheduleType(String metScheduleType) {
		this.metScheduleType = metScheduleType;
	}
	public String getMetScheduleTitle() {
		return metScheduleTitle;
	}
	public void setMetScheduleTitle(String metScheduleTitle) {
		this.metScheduleTitle = metScheduleTitle;
	}
	public String getMetScheduleContent() {
		return metScheduleContent;
	}
	public void setMetScheduleContent(String metScheduleContent) {
		this.metScheduleContent = metScheduleContent;
	}
	public Date getMetScheduleStartDate() {
		return metScheduleStartDate;
	}
	public void setMetScheduleStartDate(Date metScheduleStartDate) {
		this.metScheduleStartDate = metScheduleStartDate;
	}
	public Date getMetScheduleStartTime() {
		return metScheduleStartTime;
	}
	public void setMetScheduleStartTime(Date metScheduleStartTime) {
		this.metScheduleStartTime = metScheduleStartTime;
	}
	public Date getMetScheduleEndDate() {
		return metScheduleEndDate;
	}
	public void setMetScheduleEndDate(Date metScheduleEndDate) {
		this.metScheduleEndDate = metScheduleEndDate;
	}
	public Date getMetScheduleEndTime() {
		return metScheduleEndTime;
	}
	public void setMetScheduleEndTime(Date metScheduleEndTime) {
		this.metScheduleEndTime = metScheduleEndTime;
	}
	
	
}