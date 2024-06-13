package com.kosta.care.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

import com.kosta.care.dto.CalendarDto;
import com.kosta.care.service.CalendarService;

public class CalendarController {
	private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping("/schedules")
    public List<CalendarDto> getAllSchedules() {
        return calendarService.getAllSchedules();
    }

}
