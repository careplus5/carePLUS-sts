package com.kosta.care.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kosta.care.dto.CalendarDto;
import com.kosta.care.dto.SurgeryRequestDto;
import com.kosta.care.dto.TestDto;
import com.kosta.care.entity.DiagnosisDue;
import com.kosta.care.entity.DocSchedule;
import com.kosta.care.entity.MetSchedule;
import com.kosta.care.entity.Patient;
import com.kosta.care.entity.Test;
//import com.kosta.care.repository.DiagnosisDueRepository;
import com.kosta.care.repository.DocScheduleRepository;
import com.kosta.care.repository.MetScheduleRepository;
//import com.kosta.care.repository.PatientRepository;
//import com.kosta.care.repository.SurgeryRequestRepository;
//import com.kosta.care.repository.TestRepository;

@Service
public class CalendarServiceImpl implements CalendarService {
//	 	@Autowired
//	    private SurgeryRequestRepository surgeryRequestRepository;
//	    @Autowired
//	    private DiagnosisDueRepository diagnosisDueRepository;
//	    @Autowired
//	    private TestRepository testRepository;
//	    @Autowired
//	    private PatientRepository patientRepository;
	
		@Autowired
		private DocScheduleRepository docScheduleRepository;
		@Autowired
		private MetScheduleRepository metScheduleRepository;


	

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
	
//	@Override
//    public Map<String,Object> getAllTestTimeList(Long userId) throws Exception {
//    	if (userId == null) {
//			throw new UsernameNotFoundException("empNum이 Null 찍히는데요?");
//		}
//    	
//    	Map<String, Object> res = new HashMap<>();
//
////		String jobString = userId+"".substring(0, 2); <<이게 안돼요 ㅠ
//		
//		String jobString = userId.toString();
//		// 추가적인 로그 출력
////		System.out.println("userId: " + userId);
////		System.out.println("jobString: " + jobString);
//
//		String findJob = jobString.substring(0,2);
//		System.out.println(findJob);
//		
//		switch (findJob) {
//		case "11":
//			List<SurgeryRequestDto> surSchedules= surgeryRequestRepository.findByDocNum(userId).stream()
//				.map(sr-> SurgeryRequestDto.builder()
//							.surgeryRequestNum(sr.getSurgeryRequestNum())
//							.patNum(sr.getPatNum())
//							.surDate(sr.getSurDate())
//							.docNum(sr.getDocNum())
//							.build()).collect(Collectors.toList());
//			
//			res.put("surSchedules", surSchedules );
//			
//			List<DiagnosisDue> digSchedules = diagnosisDueRepository.findByDocNum(userId);
//			res.put("digSchedules", digSchedules );
//			break;
//		case "14":
//			List<Test> testList = testRepository.findByMetNum(userId);
//			List<TestDto> schedules = testList.stream()
//				            .map(t->{
//				            	TestDto testDto = TestDto.builder().build();
//				            	Optional<Patient> opatient = patientRepository.findById(t.getPatNum());
//				            	if(opatient.isPresent()) {
//				            		Patient patient = opatient.get();
//				    	    		testDto.setPatName(patient.getPatName());
//				    	    		testDto.setPatJumin(patient.getPatJumin());
//				    	    		testDto.setPatGender(patient.getPatGender());
//				    	    		testDto.setPatBloodType(patient.getPatBloodType());
//				            	}
//				            	testDto.setTestNum(t.getTestNum());
//				            	testDto.setMetNum(t.getMetNum());
//				            	testDto.setPatNum(t.getPatNum());
//				            	testDto.setTestName(t.getTestName());				         
//				            	testDto.setTestPart(t.getTestPart());
//				            	testDto.setTestAppointmentDate(t.getTestAppointmentDate());
//				            	testDto.setTestAppointmentTime(t.getTestAppointmentTime());
//				            	
//				            	return testDto;
//				            })
//				            .collect(Collectors.toList());
//			
//			res.put("schedules", schedules );
//			break;
//		default:
//			throw new Exception("Unexpected value: " + jobString);
//		}
//		return res;
//    }



}
