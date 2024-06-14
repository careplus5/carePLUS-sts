package com.kosta.care.service;

import java.util.List;

import com.kosta.care.dto.CalendarDto;

public interface CalendarService {
	List<CalendarDto> getAllSchedules(Long empNum) throws Exception;
}
