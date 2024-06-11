package com.kosta.care.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	public String sendAlarmByToken(Long empNum, String alarmTitle, String alarmContent) throws Exception {
		
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
		System.out.println(FcmToken);
		//DB에 알림 생성
		Alarm alarm = Alarm.builder()
				.empNum(empNum)
				.alarmCheck(false)
				.alarmDelivery(false)
				.alarmTitle(alarmTitle)
				.alarmContent(alarmContent)
				.build();
		
		alarmRepository.save(alarm);
		
		//FCM메시지 만들기
		Notification notification = Notification.builder()
				.setTitle(alarm.getAlarmNum()+"")
				.setBody(alarmContent).build();

		Message message = Message.builder()
				.setToken(FcmToken)
				.setNotification(notification).build();
		//알림 끄기를 안했을 시에만!	알림 전송
		if(findJob.equals("11")) {
			Optional<Doctor> employee = doctorRepository.findById(empNum);
			if(employee.get().getIsNoticeAlarmOk().equals(true)) {
				firebaseMessaging.send(message);
				alarm.setAlarmDelivery(true);
			}
		}else if(findJob.equals("12")) {
			Optional<Nurse>employee = nurseRepository.findById(empNum);
			if(employee.get().getIsNoticeAlarmOk().equals(true)) {
				firebaseMessaging.send(message);
				alarm.setAlarmDelivery(true);
			}
		}else if(findJob.equals("13")) {
			Optional<AdminHospital>employee = adminHospitalRepository.findById(empNum);
			if(employee.get().getIsNoticeAlarmOk().equals(true)) {
				firebaseMessaging.send(message);
				alarm.setAlarmDelivery(true);
			}
		}else if(findJob.equals("14")) {
			Optional<MedicalTechnician>employee = medicalTechnicianRepository.findById(empNum);
			if(employee.get().getIsNoticeAlarmOk().equals(true)) {
				firebaseMessaging.send(message);
				alarm.setAlarmDelivery(true);
			}
		}
		return "전송 성공";
	}
	
	//알림 재발송
	@Override
	public List<AlarmDto> sendNotCheckAlarm(Long empNum) throws Exception{
		//미확인 알림 리스트 가져오기
		List<Alarm> alarmList = alarmRepository.findByEmpNumAndAlarmCheckFalse(empNum);
		
		List<AlarmDto> alarmDtoList = alarmList.stream()
				.map(alarm->AlarmDto.builder()
					.AlarmNum(alarm.getAlarmNum())
					.isCheck(alarm.getAlarmCheck())
					.title(alarm.getAlarmTitle())
					.content(alarm.getAlarmContent())
					.build()).collect(Collectors.toList());
		return alarmDtoList;
	}
	
	//특정알람 확인(알람번호)
	public Long checkAlarm(Long alarmNum) throws Exception{
		Optional<Alarm> alarm = alarmRepository.findById(alarmNum);
		alarm.get().setAlarmCheck(true);
		alarmRepository.save(alarm.get());
		return alarmNum;
	}
	//프론트에서 받은 fcmToken DB에 저장
	public void registFcmToken(String fcmToken, Long empNum)throws Exception{
		String empNumString = empNum.toString();
		String findJob = empNumString.substring(0,2);
		if(findJob.equals("11")) {
			Optional<Doctor> employee = doctorRepository.findById(empNum);
			employee.get().setFcmToken(fcmToken);
			doctorRepository.save(employee.get());
		}else if(findJob.equals("12")) {
			Optional<Nurse>employee = nurseRepository.findById(empNum);;
			employee.get().setFcmToken(fcmToken);
			nurseRepository.save(employee.get());
		}else if(findJob.equals("13")) {
			Optional<AdminHospital>employee = adminHospitalRepository.findById(empNum);
			employee.get().setFcmToken(fcmToken);
			adminHospitalRepository.save(employee.get());
		}else if(findJob.equals("14")) {
			Optional<MedicalTechnician>employee = medicalTechnicianRepository.findById(empNum);
			employee.get().setFcmToken(fcmToken);
			medicalTechnicianRepository.save(employee.get());
		}
	}
	//알림 리스트 삭제
	public Boolean deleteAlarmList (Long empNum)throws Exception{
		//확인이 false인 알림 가져오기
		List<Alarm> alarmList = alarmRepository.findByEmpNumAndAlarmCheckFalse(empNum);
		
		try{
			for(Alarm alarm : alarmList) {
			alarm.setAlarmCheck(true);
			alarmRepository.save(alarm);
			}return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Boolean changeAlarmStatus(Long empNum) throws Exception {
		String empNumString = empNum.toString();
		String findJob = empNumString.substring(0,2);
		if(findJob.equals("11")) {
			Optional<Doctor> oemployee = doctorRepository.findById(empNum);
			oemployee.get().setIsNoticeAlarmOk(!(oemployee.get().getIsNoticeAlarmOk()));
			doctorRepository.save(oemployee.get());
		}else if(findJob.equals("12")) {
			Optional<Nurse> oemployee = nurseRepository.findById(empNum);
			oemployee.get().setIsNoticeAlarmOk(!(oemployee.get().getIsNoticeAlarmOk()));
			nurseRepository.save(oemployee.get());
		}else if(findJob.equals("13")) {
			Optional<AdminHospital> oemployee = adminHospitalRepository.findById(empNum);
			oemployee.get().setIsNoticeAlarmOk(!(oemployee.get().getIsNoticeAlarmOk()));
			adminHospitalRepository.save(oemployee.get());
		}else if(findJob.equals("14")) {
			Optional<MedicalTechnician> oemployee = medicalTechnicianRepository.findById(empNum);
			oemployee.get().setIsNoticeAlarmOk(!(oemployee.get().getIsNoticeAlarmOk()));
			medicalTechnicianRepository.save(oemployee.get());
		}
		return true;
	}

	@Override
	public Boolean checkAlarmStatus(Long empNum) throws Exception {
		String empNumString = empNum.toString();
		String findJob = empNumString.substring(0,2);
		if(findJob.equals("11")) {
			Optional<Doctor> employee = doctorRepository.findById(empNum);
			return employee.get().getIsNoticeAlarmOk();
		}else if(findJob.equals("12")) {
			Optional<Nurse>employee = nurseRepository.findById(empNum);;
			return employee.get().getIsNoticeAlarmOk();
		}else if(findJob.equals("13")) {
			Optional<AdminHospital>employee = adminHospitalRepository.findById(empNum);
			return employee.get().getIsNoticeAlarmOk();
		}else if(findJob.equals("14")) {
			Optional<MedicalTechnician>employee = medicalTechnicianRepository.findById(empNum);
			return employee.get().getIsNoticeAlarmOk();
		}
		return null;
	}
}
