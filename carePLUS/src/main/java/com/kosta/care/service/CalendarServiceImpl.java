package com.kosta.care.service;

import java.util.ArrayList;
import java.util.List;

import com.kosta.care.dto.CalendarDto;
import com.kosta.care.entity.DocSchedule;
import com.kosta.care.entity.MetSchedule;
import com.kosta.care.entity.NurseSchedule;
import com.kosta.care.repository.DocScheduleRepository;
import com.kosta.care.repository.MetScheduleRepository;
import com.kosta.care.repository.NurseScheduleRepository;

public class CalendarServiceImpl implements CalendarService {
	private final DocScheduleRepository docScheduleRepository;
    private final NurseScheduleRepository nurseScheduleRepository;
    private final MetScheduleRepository metScheduleRepository;
    

    public CalendarServiceImpl(
    	DocScheduleRepository docScheduleRepository,
        NurseScheduleRepository nurseScheduleRepository,
        MetScheduleRepository metScheduleRepository
    ) {
        this.docScheduleRepository = docScheduleRepository;
        this.nurseScheduleRepository = nurseScheduleRepository;
        this.metScheduleRepository = metScheduleRepository;
    }

    @Override
    public List<CalendarDto> getAllSchedules() {
        List<CalendarDto> docSchedules = docScheduleRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());

        List<CalendarDto> nurseSchedules = nurseScheduleRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());

        List<CalendarDto> metSchedules = metScheduleRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());

        List<CalendarDto> allSchedules = new ArrayList<>();
        allSchedules.addAll(docSchedules);
        allSchedules.addAll(nurseSchedules);
        allSchedules.addAll(metSchedules);

        return allSchedules;
    }

    private CalendarDto toDTO(DocSchedule schedule) {
    	CalendarDto dto = new CalendarDto();
        dto.setId(schedule.getDocScheduleNum());
        dto.setEmpNum(schedule.getDocNum());
        dto.setJobNum(schedule.getJobNum());
        dto.setScheduleType(schedule.getDocScheduleType()); //수술인지 진료인지
        dto.setTitle(schedule.getDocScheduleTitle()); // 환자번호
        dto.setContent(schedule.getDocScheduleContent()); // 진료번호, 수술번호
        dto.setStartDate(schedule.getDocScheduleStartDate());
        dto.setStartTime(schedule.getDocScheduleStartTime());
        dto.setEndDate(schedule.getDocScheduleEndDate());
        dto.setEndTime(schedule.getDocScheduleEndTime());
        return dto;
    }

    private CalendarDto toDTO(NurseSchedule schedule) {
    	CalendarDto dto = new CalendarDto();
        dto.setId(schedule.getNurScheduleNum());
        dto.setEmpNum(schedule.getNurNum()); //사번으로 수술간호사 처리 가능한가?
        dto.setJobNum(schedule.getJobNum());
        dto.setScheduleType(schedule.getNurScheduleType()); //수술간호사 스케
        dto.setTitle(schedule.getNurScheduleTitle()); // 환자번호
        dto.setContent(schedule.getNurScheduleContent()); // 수술번호 (수술실번호는..?)
        dto.setStartDate(schedule.getNurScheduleStartDate());
        dto.setStartTime(schedule.getNurScheduleStartTime());
        dto.setEndDate(schedule.getNurScheduleEndDate());
        dto.setEndTime(schedule.getNurScheduleEndTime());
        return dto;
    }

    private CalendarDto toDTO(MetSchedule schedule) {
    	CalendarDto dto = new CalendarDto();
        dto.setId(schedule.getMetScheduleNum());
        dto.setEmpNum(schedule.getMetNum()); // MRI, CT, X-ray ... 구분가능?
        dto.setJobNum(schedule.getJobNum());
        dto.setScheduleType(schedule.getMetScheduleType()); // 검사실 스케줄
        dto.setTitle(schedule.getMetScheduleTitle()); // 환자번호
        dto.setContent(schedule.getMetScheduleContent()); // 수술번호 (수술실번호는..?)
        dto.setStartDate(schedule.getMetScheduleStartDate());
        dto.setStartTime(schedule.getMetScheduleStartTime());
        dto.setEndDate(schedule.getMetScheduleEndDate());
        dto.setEndTime(schedule.getMetScheduleEndTime());
        return dto;
    }


}
