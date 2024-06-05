package com.kosta.care.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;

import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.kosta.care.dto.AlarmDto;
import com.kosta.care.entity.AdminHospital;
import com.kosta.care.entity.Alarm;
import com.kosta.care.entity.Doctor;
import com.kosta.care.entity.MedicalTechnician;
import com.kosta.care.entity.Nurse;
import com.kosta.care.repository.AdminHospitalRepository;
import com.kosta.care.repository.AlarmRepository;
import com.kosta.care.repository.DoctorRepository;
import com.kosta.care.repository.EmployeeAlarmRepository;
import com.kosta.care.repository.MedicalTechnicianRepository;
import com.kosta.care.repository.NurseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {
	private final FirebaseMessaging firebaseMessaging;
	private final DoctorRepository doctorRepository;
	private final NurseRepository nurseRepository;
	private final AdminHospitalRepository adminHospitalRepository;
	private final MedicalTechnicianRepository medicalTechnicianRepository;
	private final AlarmRepository alarmRepository;
	private final EmployeeAlarmRepository employeeAlarmRepository;
	
	@Override
	public String sendAlarmByToken(Long empNum, AlarmDto alarmDto) {
		
		//empNum로 맞는 Repository를 찾아서 토큰 값 불러오기
		String empNumString = empNum.toString();
		String findJob = empNumString.substring(0,2);
		String FcmToken = null;
		if(findJob.equals("11")) {
			Optional<Doctor> employee = doctorRepository.findById(empNum);
			FcmToken = employee.get().getFcmToken();
		}else if(findJob.equals("12")) {
			Optional<Nurse>employee = nurseRepository.findById(empNum);
			FcmToken = employee.get().getFcmToken();
		}else if(findJob.equals("13")) {
			Optional<AdminHospital>employee = adminHospitalRepository.findById(empNum);
			FcmToken = employee.get().getFcmToken();
		}else if(findJob.equals("14")) {
			Optional<MedicalTechnician>employee = medicalTechnicianRepository.findById(empNum);
			FcmToken = employee.get().getFcmToken();
		}
		
		//DB에 알림 생성
		Alarm alarm = Alarm.builder()
				.empNum(empNum)
				.alarmTitle(alarmDto.getTitle())
				.alarmContent(alarmDto.getContent())
				.build();
		
		alarmRepository.save(alarm);
		
		//FCM메시지(알림) 전송
		Notification notification = Notification.builder()
				.setTitle(alarmDto.getTitle())
				.setBody(alarmDto.getContent())
				.build();
		
		Message message = Message.builder()
				.setToken(FcmToken)
				.setNotification(notification)
				.build();
		
		try {
			firebaseMessaging.send(message);
			alarm.setAlarmDelivery(true);
			return "알림 전송 성공";
		}catch (Exception e) {
			e.printStackTrace();
			return "알림 전송 실패";
		}
	}
	
	//알림 재발송
	@Override
	public List<AlarmDto> sendNotCheckAlarm(Long empNum){
		//empNum으로 보내줄 대상 Token값 조회
		String empNumString = empNum.toString();
		String findJob = empNumString.substring(0,2);
		String FcmToken = null;
		if(findJob.equals("11")) {
			Optional<Doctor> employee = doctorRepository.findById(empNum);
			FcmToken = employee.get().getFcmToken();
		}else if(findJob.equals("12")) {
			Optional<Nurse>employee = nurseRepository.findById(empNum);
			FcmToken = employee.get().getFcmToken();
		}else if(findJob.equals("13")) {
			Optional<AdminHospital>employee = adminHospitalRepository.findById(empNum);
			FcmToken = employee.get().getFcmToken();
		}else if(findJob.equals("14")) {
			Optional<MedicalTechnician>employee = medicalTechnicianRepository.findById(empNum);
			FcmToken = employee.get().getFcmToken();
		}
		//미확인 알림 리스트 가져오기
		List<Alarm> alarmList = employeeAlarmRepository.AlarmListFindByEmpNumAndAlarmDeliveryFalse(empNum);
		
		List<AlarmDto> alarmDtoList = new ArrayList<>();
		
		//알림 리스트 Dto로 변경하여 뿌려주기 재시도
		for(Alarm alarm : alarmList) {
			alarmDtoList.add(AlarmDto.builder()
					.title(alarm.getAlarmTitle())
					.content(alarm.getAlarmContent())
					.build());
			
		}//for문종료
		return alarmDtoList;
	}//재발송 종료
	
	//특정알람 확인(알람번호)

	//프론트에서 받은 fcmToken DB에 저장
//	public void registFcmToken(String fcmToken) {
//
//	}
}
