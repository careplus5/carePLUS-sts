package com.kosta.care.entity;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.api.client.util.DateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Surgery {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long surgeryNum;
	@Column
	private Long docNum;
	@Column
	private Long nurNum1;
	@Column
	private Long nurNum2;
	@Column
	private Long nurNum3;
	@Column
	private Long departmentNum;
	@Column
	private Long patNum;
	@Column
	private Long surgeryRequestNum;
	@Column
	private Date surgeryDueDate;
	@Column
	private Time surgeryDueStartTime;  //수술시작예정시간 (원무과는 여기에 예약시간 insert)
	@Column
	private Time surgeryStartTime;
	@Column
	private Time surgeryPeriodTime;
	@Column
	private Time surgeryTotalTime;
	@Column
	private Time surgeryDueEnd;
	@Column
	private String surgeryPatStatus;
	@Column
	private String surgeryAnesthesia;
	@Column
	private String surgeryAnesthesiaPart;
	@Column
	private String surgeryBloodPack;
	@Column
	private String surgeryBloodPackCnt;
	@Column
	private String surgeryResult;
	@Column  // 여기에다 대기중 기본값으로 
	private String surgeryState;
	@Column
	private String surgeryEtc;
	@Column
	private Long operationRoomNum;
	
}
