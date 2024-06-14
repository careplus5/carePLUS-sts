package com.kosta.care.dto;

import java.sql.Date;

public class CalendarDto {
	private Long id;
    private Long empNum; 
    private Long jobNum; 
    private String ScheduleType;
    private String Title;
    private String Content;
    private Date startDate;
    private Date startTime;
    private Date endDate;
    private Date endTime;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getEmpNum() {
		return empNum;
	}
	public void setEmpNum(Long empNum) {
		this.empNum = empNum;
	}
	public Long getJobNum() {
		return jobNum;
	}
	public void setJobNum(Long jobNum) {
		this.jobNum = jobNum;
	}
	public String getScheduleType() {
		return ScheduleType;
	}
	public void setScheduleType(String scheduleType) {
		ScheduleType = scheduleType;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	@Override
	public String toString() {
		return "CalendarDto [id=" + id + ", empNum=" + empNum + ", jobNum=" + jobNum + ", ScheduleType=" + ScheduleType
				+ ", Title=" + Title + ", Content=" + Content + ", startDate=" + startDate + ", startTime=" + startTime
				+ ", endDate=" + endDate + ", endTime=" + endTime + "]";
	}

	
    
}
