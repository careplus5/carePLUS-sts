package com.kosta.care.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kosta.care.dto.CalendarDto;
import com.kosta.care.entity.DocSchedule;
import com.kosta.care.entity.MetSchedule;
import com.kosta.care.entity.NurseSchedule;
import com.kosta.care.repository.DocScheduleRepository;
import com.kosta.care.repository.MetScheduleRepository;
import com.kosta.care.repository.NurseScheduleRepository;

@Service
public class CalendarServiceImpl implements CalendarService {
	private final DocScheduleRepository docScheduleRepository;
    private final MetScheduleRepository metScheduleRepository;
    

    public CalendarServiceImpl(
    	DocScheduleRepository docScheduleRepository,
        MetScheduleRepository metScheduleRepository
    ) {
        this.docScheduleRepository = docScheduleRepository;
        this.metScheduleRepository = metScheduleRepository;
    }

    @Override
    public List<CalendarDto> getAllSchedules(Long empNum) throws Exception{
        
        if (empNum == null) {
            throw new UsernameNotFoundException("empNum이 Null 찍히는데요?");
        }
        
        
        String jobString = empNum.toString();
     // 추가적인 로그 출력
        System.out.println("empNum: " + empNum);
        System.out.println("jobString: " + jobString);
        
		String findJob = jobString.substring(0,2);


        List<CalendarDto> schedules = new ArrayList<>();
        switch (findJob) {
            case "11":
                schedules = docScheduleRepository.findByDocNum(empNum).stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
                break;
            case "14":
                schedules = metScheduleRepository.findByMetNum(empNum).stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
                break;
            default:
                throw new Exception("Unexpected value: " + findJob);
        }
        System.out.println(schedules);
        return schedules;
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
