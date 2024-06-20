package com.kosta.care.dto;

import java.sql.Time;

import com.kosta.care.entity.Surgery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SurRecordDto {
	private Long surgeryNum;
	private String surAnesthesia;
	private String surAnesthesiaPart;
	private String surBloodPack;
	private String surBloodPackCnt;
	private Time surStartTime;
	private Time surEndTime;
	private Time surTotalTime;
	private String surResult;
	private String surEtc;
	
	public Surgery toSurRecord() {
		return Surgery.builder()
					.surgeryNum(surgeryNum)
					.surgeryAnesthesia(surAnesthesia)
					.surgeryAnesthesiaPart(surAnesthesiaPart)
					.surgeryBloodPack(surBloodPack)
					.surgeryBloodPackCnt(surBloodPackCnt)
					.surgeryStartTime(surStartTime)
					.surgeryDueEnd(surEndTime)
					.surgeryTotalTime(surTotalTime)
					.surgeryResult(surResult)
					.surgeryEtc(surEtc)
					.build();
	}
}
