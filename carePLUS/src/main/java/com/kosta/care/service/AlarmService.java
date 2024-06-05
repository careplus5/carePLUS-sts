package com.kosta.care.service;

import java.util.List;

import com.kosta.care.dto.AlarmDto;

public interface AlarmService {
	String sendAlarmByToken(Long empNum, AlarmDto alarmDto) throws Exception;
	public List<AlarmDto> sendNotCheckAlarm(Long empNum) throws Exception;
}
