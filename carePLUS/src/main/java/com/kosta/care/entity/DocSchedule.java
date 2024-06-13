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
	private Long docScheduleNum;
	@Column
	private Long docNum;
	@Column
	private Long jobNum;
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
	@Column
	private Date docScheduleEndTime;
	
	public Long getDocScheduleNum() {
		return docScheduleNum;
	}
	public void setDocScheduleNum(Long docScheduleNum) {
		this.docScheduleNum = docScheduleNum;
	}
	public Long getDocNum() {
		return docNum;
	}
	public void setDocNum(Long docNum) {
		this.docNum = docNum;
	}
	public Long getJobNum() {
		return jobNum;
	}
	public void setJobNum(Long jobNum) {
		this.jobNum = jobNum;
	}
	public String getDocScheduleType() {
		return docScheduleType;
	}
	public void setDocScheduleType(String docScheduleType) {
		this.docScheduleType = docScheduleType;
	}
	public String getDocScheduleTitle() {
		return docScheduleTitle;
	}
	public void setDocScheduleTitle(String docScheduleTitle) {
		this.docScheduleTitle = docScheduleTitle;
	}
	public String getDocScheduleContent() {
		return docScheduleContent;
	}
	public void setDocScheduleContent(String docScheduleContent) {
		this.docScheduleContent = docScheduleContent;
	}
	public Date getDocScheduleStartDate() {
		return docScheduleStartDate;
	}
	public void setDocScheduleStartDate(Date docScheduleStartDate) {
		this.docScheduleStartDate = docScheduleStartDate;
	}
	public Date getDocScheduleStartTime() {
		return docScheduleStartTime;
	}
	public void setDocScheduleStartTime(Date docScheduleStartTime) {
		this.docScheduleStartTime = docScheduleStartTime;
	}
	public Date getDocScheduleEndDate() {
		return docScheduleEndDate;
	}
	public void setDocScheduleEndDate(Date docScheduleEndDate) {
		this.docScheduleEndDate = docScheduleEndDate;
	}
	public Date getDocScheduleEndTime() {
		return docScheduleEndTime;
	}
	public void setDocScheduleEndTime(Date docScheduleEndTime) {
		this.docScheduleEndTime = docScheduleEndTime;
	}
	
}