package com.kosta.care.service;

import java.util.List;

import com.kosta.care.dto.AlarmDto;

public interface AlarmService {
	String sendAlarmByToken(Long empNum, String alarmTitle, String alarmContent) throws Exception;
	List<AlarmDto> sendNotCheckAlarm(Long empNum) throws Exception;
	Long checkAlarm(Long alarmNum) throws Exception;
	void registFcmToken(String fcmToken, Long empNum) throws Exception;
	Boolean deleteAlarmList (Long empNum) throws Exception;
	Boolean changeAlarmStatus(Long empNum) throws Exception;
	Boolean checkAlarmStatus(Long empNum) throws Exception;
}
