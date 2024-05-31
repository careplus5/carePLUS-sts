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
public class AdmSchecule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer admScheduleNum;
	@Column
	private Integer admNum;
	@Column
	private Integer jobNum;
	@Column
	private String admScheduleType;
	@Column
	private String admScheduleTitle;
	@Column
	private String admScheduleContent;
	@Column
	private Date admScheduleStartDate;
	@Column
	private Date admScheduleStartTime;
	@Column
	private Date admScheduleEndDate;
}
