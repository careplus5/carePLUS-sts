package com.kosta.care.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

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

	@Override
	public void sendAlarmByEmpNum(Long empNum, String alarmCategory, String alarmContent) throws Exception {
		System.out.println("sendAlarmService :"+empNum);
		// empNum로 맞는 Repository를 찾아서 토큰 값 불러오기
		String empNumString = empNum.toString();
		String findJob = empNumString.substring(0, 2);
		String FcmToken = null;
		if (findJob.equals("11")) {
			Optional<Doctor> employee = doctorRepository.findById(empNum);
			FcmToken = employee.get().getFcmToken();
		} else if (findJob.equals("12")) {
			Optional<Nurse> employee = nurseRepository.findById(empNum);
			FcmToken = employee.get().getFcmToken();
		} else if (findJob.equals("13")) {
			Optional<AdminHospital> employee = adminHospitalRepository.findById(empNum);
			FcmToken = employee.get().getFcmToken();
		} else if (findJob.equals("14")) {
			Optional<MedicalTechnician> employee = medicalTechnicianRepository.findById(empNum);
			FcmToken = employee.get().getFcmToken();
		}else System.out.println("관리자 입니다");
		System.out.println(FcmToken);
		// DB에 알림 생성
		Alarm alarm = Alarm.builder().empNum(empNum).alarmCheck(false).alarmDelivery(false).alarmCategory(alarmCategory)
				.alarmContent(alarmContent).build();

		alarmRepository.save(alarm);
		
		Message message = null;

		Notification notification = null;
		if (findJob.equals("11")) {
			Optional<Doctor> employee = doctorRepository.findById(empNum);
			System.out.println(employee);
			if (alarmCategory.equals("공지사항") && employee.get().getIsNoticeAlarmOk().equals(true)) {

				notification = Notification.builder()
			            .setTitle(alarmCategory)
			            .setBody("(" + alarmCategory + ")" + alarmContent)
			            .build();
				if(FcmToken!=null) {
				message = Message.builder()
						.setToken(FcmToken)
						.setNotification(notification)
						.putData("alarmNum", alarm.getAlarmNum().toString())
			            .putData("alarmCategory", alarmCategory)
			            .putData("alarmSendFlag", "true")
						.build();
				}
			} else if (alarmCategory.equals("진료") && employee.get().getIsDiagnosAlarmOk().equals(true)) {

				notification = Notification.builder()
			            .setTitle(alarmCategory)
			            .setBody("(" + alarmCategory + ")" + alarmContent)
			            .build();
				
				if(FcmToken!=null) {
				message = Message.builder()
						.setToken(FcmToken)
						.setNotification(notification)
						.putData("alarmNum", alarm.getAlarmNum().toString())
			            .putData("alarmCategory", alarmCategory)
			            .putData("alarmSendFlag", "true")
						.build();
				}
			} else if (alarmCategory.equals("수술") && employee.get().getIsSurgeryAlarmOk().equals(true)) {

				notification = Notification.builder()
			            .setTitle(alarmCategory)
			            .setBody("(" + alarmCategory + ")" + alarmContent)
			            .build();
				
				if(FcmToken!=null) {
				message = Message.builder()
						.setToken(FcmToken)
						.setNotification(notification)
						.putData("alarmNum", alarm.getAlarmNum().toString())
			            .putData("alarmCategory", alarmCategory)
			            .putData("alarmSendFlag", "true")
						.build();
				}

			} else {

				notification = Notification.builder()
			            .setTitle(alarmCategory)
			            .setBody("(" + alarmCategory + ")" + alarmContent)
			            .build();
				
				if(FcmToken!=null) {
				message = Message.builder()
						.setToken(FcmToken)
						.setNotification(notification)
						.putData("alarmNum", alarm.getAlarmNum().toString())
			            .putData("alarmCategory", alarmCategory)
			            .putData("alarmSendFlag", "false")
						.build();
				}

			}
		} else if (findJob.equals("12")) {
			Optional<Nurse> employee = nurseRepository.findById(empNum);
			if (alarmCategory.equals("공지사항") && employee.get().getIsNoticeAlarmOk().equals(true)) {

				notification = Notification.builder()
			            .setTitle(alarmCategory)
			            .setBody("(" + alarmCategory + ")" + alarmContent)
			            .build();
				
				if(FcmToken!=null) {
				message = Message.builder()
						.setToken(FcmToken)
						.setNotification(notification)
						.putData("alarmNum", alarm.getAlarmNum().toString())
			            .putData("alarmCategory", alarmCategory)
			            .putData("alarmSendFlag", "true")
						.build();
				}

			} else if (alarmCategory.equals("수술") && employee.get().getIsSurgeryAlarmOk().equals(true)) {

				notification = Notification.builder()
			            .setTitle(alarmCategory)
			            .setBody("(" + alarmCategory + ")" + alarmContent)
			            .build();
				
				if(FcmToken!=null) {
				message = Message.builder()
						.setToken(FcmToken)
						.setNotification(notification)
						.putData("alarmNum", alarm.getAlarmNum().toString())
			            .putData("alarmCategory", alarmCategory)
			            .putData("alarmSendFlag", "true")
						.build();
				}

			} else if (alarmCategory.equals("입원") && employee.get().getIsAdmissionAlarmOk().equals(true)) {

				notification = Notification.builder()
			            .setTitle(alarmCategory)
			            .setBody("(" + alarmCategory + ")" + alarmContent)
			            .build();
				
				if(FcmToken!=null) {
				message = Message.builder()
						.setToken(FcmToken)
						.setNotification(notification)
						.putData("alarmNum", alarm.getAlarmNum().toString())
			            .putData("alarmCategory", alarmCategory)
			            .putData("alarmSendFlag", "true")
						.build();
				}

			} else if (alarmCategory.equals("요청사항") && employee.get().getIsRequestAlarmOk().equals(true)) {

				notification = Notification.builder()
			            .setTitle(alarmCategory)
			            .setBody("(" + alarmCategory + ")" + alarmContent)
			            .build();
				
				if(FcmToken!=null) {
				message = Message.builder()
						.setToken(FcmToken)
						.setNotification(notification)
						.putData("alarmNum", alarm.getAlarmNum().toString())
			            .putData("alarmCategory", alarmCategory)
			            .putData("alarmSendFlag", "true")
						.build();
				}

			} else {

				notification = Notification.builder()
			            .setTitle(alarmCategory)
			            .setBody("(" + alarmCategory + ")" + alarmContent)
			            .build();
				
				if(FcmToken!=null) {
				message = Message.builder()
						.setToken(FcmToken)
						.setNotification(notification)
						.putData("alarmNum", alarm.getAlarmNum().toString())
			            .putData("alarmCategory", alarmCategory)
			            .putData("alarmSendFlag", "false")
						.build();
				}

			}
		} else if (findJob.equals("13")) {
			Optional<AdminHospital> employee = adminHospitalRepository.findById(empNum);
			if (alarmCategory.equals("공지사항") && employee.get().getIsNoticeAlarmOk().equals(true)) {

				notification = Notification.builder()
			            .setTitle(alarmCategory)
			            .setBody("(" + alarmCategory + ")" + alarmContent)
			            .build();
				
				if(FcmToken!=null) {
				message = Message.builder()
						.setToken(FcmToken)
						.setNotification(notification)
						.putData("alarmNum", alarm.getAlarmNum().toString())
			            .putData("alarmCategory", alarmCategory)
			            .putData("alarmSendFlag", "true")
						.build();
				}

			} else if (alarmCategory.equals("처방") && employee.get().getIsPrescriptionAlarmOk().equals(true)) {

				notification = Notification.builder()
			            .setTitle(alarmCategory)
			            .setBody("(" + alarmCategory + ")" + alarmContent)
			            .build();
				
				if(FcmToken!=null) {
				message = Message.builder()
						.setToken(FcmToken)
						.setNotification(notification)
						.putData("alarmNum", alarm.getAlarmNum().toString())
			            .putData("alarmCategory", alarmCategory)
			            .putData("alarmSendFlag", "true")
						.build();
				}
			} else if (alarmCategory.equals("퇴원") && employee.get().getIsDischargeAlarmOk().equals(true)) {

				notification = Notification.builder()
			            .setTitle(alarmCategory)
			            .setBody("(" + alarmCategory + ")" + alarmContent)
			            .build();
				
				if(FcmToken!=null) {
				message = Message.builder()
						.setToken(FcmToken)
						.setNotification(notification)
						.putData("alarmNum", alarm.getAlarmNum().toString())
			            .putData("alarmCategory", alarmCategory)
			            .putData("alarmSendFlag", "true")
						.build();
				}

			} else {

				notification = Notification.builder()
			            .setTitle(alarmCategory)
			            .setBody("(" + alarmCategory + ")" + alarmContent)
			            .build();
				
				if(FcmToken!=null) {
				message = Message.builder()
						.setToken(FcmToken)
						.setNotification(notification)
						.putData("alarmNum", alarm.getAlarmNum().toString())
			            .putData("alarmCategory", alarmCategory)
			            .putData("alarmSendFlag", "false")
						.build();
				}

			}
		} else if (findJob.equals("14")) {
			Optional<MedicalTechnician> employee = medicalTechnicianRepository.findById(empNum);
			if (alarmCategory.equals("공지사항") && employee.get().getIsNoticeAlarmOk().equals(true)) {

				notification = Notification.builder()
			            .setTitle(alarmCategory)
			            .setBody("(" + alarmCategory + ")" + alarmContent)
			            .build();
				
				if(FcmToken!=null) {
				message = Message.builder()
						.setToken(FcmToken)
						.setNotification(notification)
						.putData("alarmNum", alarm.getAlarmNum().toString())
			            .putData("alarmCategory", alarmCategory)
			            .putData("alarmSendFlag", "true")
						.build();
				}

			} else {

				notification = Notification.builder()
			            .setTitle(alarmCategory)
			            .setBody("(" + alarmCategory + ")" + alarmContent)
			            .build();
				
				if(FcmToken!=null) {
				message = Message.builder()
						.setToken(FcmToken)
						.setNotification(notification)
						.putData("alarmNum", alarm.getAlarmNum().toString())
			            .putData("alarmCategory", alarmCategory)
			            .putData("alarmSendFlag", "false")
						.build();
				}

			}
		}
		try {
			firebaseMessaging.send(message);
			alarm.setAlarmDelivery(true);
		}catch (Exception e) {
			System.out.println("fcm토큰 없음");
		}
	}

	@Override
	public void sendAlarmListByJobNum(Long jobNum, String alarmContent, String alarmCategoy) throws Exception {
		System.out.println("알람리스트:"+jobNum+alarmContent);
		if (jobNum==11) {
			List<Doctor> employeeList = doctorRepository.findAll();
			for (Doctor doctor : employeeList) {
				sendAlarmByEmpNum(doctor.getDocNum(), alarmContent, alarmCategoy);
			}
		} else if (jobNum==12) {
			List<Nurse> employeeList = nurseRepository.findAll();;
			for (Nurse nurse : employeeList) {
				sendAlarmByEmpNum(nurse.getNurNum(), alarmContent, alarmCategoy);
			}
		} else if (jobNum==13) {
			List<AdminHospital> employeeList = adminHospitalRepository.findAll();;
			for (AdminHospital adminHospital : employeeList) {
				sendAlarmByEmpNum(adminHospital.getAdmNum(), alarmContent, alarmCategoy);
			}
		} else if (jobNum==14) {
			List<MedicalTechnician> employeeList = medicalTechnicianRepository.findAll();;
			for (MedicalTechnician medicalTechnician : employeeList) {
				sendAlarmByEmpNum(medicalTechnician.getMetNum(), alarmContent, alarmCategoy);
			}
		}
	}

	@Override
	public void sendAlarmListByDepartmentNum(String departmentName, String alarmContent, String alarmCategoty) throws Exception {
		
		
	}
	
	// 알림 재발송
	@Override
	public List<AlarmDto> sendNotCheckAlarm(Long empNum) throws Exception {
		// 미확인 알림 리스트 가져오기
		List<Alarm> alarmList = alarmRepository.findByEmpNumAndAlarmCheckFalse(empNum);

		List<AlarmDto> alarmDtoList = alarmList.stream()
				.map(alarm -> AlarmDto.builder().AlarmNum(alarm.getAlarmNum()).isCheck(alarm.getAlarmCheck())
						.category(alarm.getAlarmCategory()).content(alarm.getAlarmContent()).build())
				.collect(Collectors.toList());
		return alarmDtoList;
	}

	// 특정알람 확인(알람번호)
	public Long checkAlarm(Long alarmNum) throws Exception {
		Optional<Alarm> alarm = alarmRepository.findById(alarmNum);
		alarm.get().setAlarmCheck(true);
		alarmRepository.save(alarm.get());
		return alarmNum;
	}

	// 프론트에서 받은 fcmToken DB에 저장
	public void registFcmToken(String fcmToken, Long empNum) throws Exception {
		String empNumString = empNum.toString();
		String findJob = empNumString.substring(0, 2);
		if (findJob.equals("11")) {
			Optional<Doctor> employee = doctorRepository.findById(empNum);
			employee.get().setFcmToken(fcmToken);
			doctorRepository.save(employee.get());
		} else if (findJob.equals("12")) {
			Optional<Nurse> employee = nurseRepository.findById(empNum);
			employee.get().setFcmToken(fcmToken);
			nurseRepository.save(employee.get());
		} else if (findJob.equals("13")) {
			Optional<AdminHospital> employee = adminHospitalRepository.findById(empNum);
			employee.get().setFcmToken(fcmToken);
			adminHospitalRepository.save(employee.get());
		} else if (findJob.equals("14")) {
			Optional<MedicalTechnician> employee = medicalTechnicianRepository.findById(empNum);
			employee.get().setFcmToken(fcmToken);
			medicalTechnicianRepository.save(employee.get());
		}
	}

	// 알림 리스트 삭제
	public Boolean deleteAlarmList(Long empNum) throws Exception {
		// 확인이 false인 알림 가져오기
		List<Alarm> alarmList = alarmRepository.findByEmpNumAndAlarmCheckFalse(empNum);

		try {
			for (Alarm alarm : alarmList) {
				alarm.setAlarmCheck(true);
				alarmRepository.save(alarm);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	@Override
	public String changeAlarmStatus(Long empNum, String alarmName) throws Exception {
		String empNumString = empNum.toString();
		String findJob = empNumString.substring(0, 2);

		if (findJob.equals("11")) {
			Optional<Doctor> oemployee = doctorRepository.findById(empNum);
			if (oemployee.isEmpty()) throw new Exception("직원번호 오류");
			Doctor emp = oemployee.get();
			switch (alarmName) {
			case "공지사항":
				emp.setIsNoticeAlarmOk(!emp.getIsNoticeAlarmOk());
				System.out.println(emp.getIsNoticeAlarmOk());
				doctorRepository.save(emp);
				System.out.println(emp);
				return "공지사항";
			case "진료":
				emp.setIsDiagnosAlarmOk(!emp.getIsDiagnosAlarmOk());
				doctorRepository.save(emp);
				return "진료";
			case "수술":
				emp.setIsSurgeryAlarmOk(!emp.getIsSurgeryAlarmOk());
				doctorRepository.save(emp);
				return "수술";
			default:
				throw new Exception("알림 설정 이름 오류");
			}
		} else if (findJob.equals("12")) {
			Optional<Nurse> oemployee = nurseRepository.findById(empNum);
			if (oemployee.isEmpty()) throw new Exception("직원번호 오류");
			Nurse emp = oemployee.get();
			switch (alarmName) {
			case "공지사항":
				emp.setIsNoticeAlarmOk(!emp.getIsNoticeAlarmOk());
				nurseRepository.save(emp);
				return "공지사항";
			case "수술":
				emp.setIsSurgeryAlarmOk(!emp.getIsSurgeryAlarmOk());
				nurseRepository.save(emp);
				return "수술";
			case "입원":
				emp.setIsAdmissionAlarmOk(!emp.getIsAdmissionAlarmOk());
				nurseRepository.save(emp);
				return "입원";
			case "요청사항":
				emp.setIsRequestAlarmOk(!emp.getIsRequestAlarmOk());
				nurseRepository.save(emp);
				return "요청사항";
			default:
				throw new Exception("알림 설정 이름 오류");
			}
		} else if (findJob.equals("13")) {
			Optional<AdminHospital> oemployee = adminHospitalRepository.findById(empNum);
			if (oemployee.isEmpty()) throw new Exception("직원번호 오류");
			AdminHospital emp = oemployee.get();
			switch (alarmName) {
			case "공지사항":
				emp.setIsNoticeAlarmOk(!emp.getIsNoticeAlarmOk());
				adminHospitalRepository.save(emp);
				return "공지사항";
			case "처방":
				emp.setIsPrescriptionAlarmOk(!emp.getIsPrescriptionAlarmOk());
				adminHospitalRepository.save(emp);
				return "처방";
			case "퇴원":
				emp.setIsDischargeAlarmOk(!emp.getIsDischargeAlarmOk());
				adminHospitalRepository.save(emp);
				return "퇴원";
			default:
				throw new Exception("알림 설정 이름 오류");
			}
		} else if (findJob.equals("14")) {
			Optional<MedicalTechnician> oemployee = medicalTechnicianRepository.findById(empNum);
			if (oemployee.isEmpty()) throw new Exception("직원번호 오류");
			MedicalTechnician emp = oemployee.get();
			if (alarmName.equals("공지사항")) {
				emp.setIsNoticeAlarmOk(!emp.getIsNoticeAlarmOk());
				medicalTechnicianRepository.save(emp);
				return "공지사항";
			} else {
				throw new Exception("알림 설정 이름 오류");
			}
		} else {
			throw new Exception("직업 코드 오류");
		}
	}

	@Override
	public List<Boolean> checkAlarmStatus(Long empNum) throws Exception {
		String empNumString = empNum.toString();
		String findJob = empNumString.substring(0, 2);

		if (findJob.equals("11")) {
			Optional<Doctor> employee = doctorRepository.findById(empNum);
			if (employee.isPresent()) {
				List<Boolean> checkAlarm = new ArrayList<>();
				checkAlarm.add(employee.get().getIsNoticeAlarmOk());
				checkAlarm.add(employee.get().getIsDiagnosAlarmOk());
				checkAlarm.add(employee.get().getIsSurgeryAlarmOk());
				return checkAlarm;
			} else {
				throw new Exception("의사 알림 정보 조회 오류");
			}
		} else if (findJob.equals("12")) {
			Optional<Nurse> employee = nurseRepository.findById(empNum);
			if (employee.isPresent()) {
				List<Boolean> checkAlarm = new ArrayList<>();
				checkAlarm.add(employee.get().getIsNoticeAlarmOk());
				checkAlarm.add(employee.get().getIsSurgeryAlarmOk());
				checkAlarm.add(employee.get().getIsAdmissionAlarmOk());
				checkAlarm.add(employee.get().getIsRequestAlarmOk());
				return checkAlarm;
			} else {
				throw new Exception("간호사 알림 정보 조회 오류");
			}
		} else if (findJob.equals("13")) {
			Optional<AdminHospital> employee = adminHospitalRepository.findById(empNum);
			if (employee.isPresent()) {
				List<Boolean> checkAlarm = new ArrayList<>();
				checkAlarm.add(employee.get().getIsNoticeAlarmOk());
				checkAlarm.add(employee.get().getIsPrescriptionAlarmOk());
				checkAlarm.add(employee.get().getIsDischargeAlarmOk());
				return checkAlarm;
			} else {
				throw new Exception("원무과 알림 정보 조회 오류");
			}
		} else if (findJob.equals("14")) {
			Optional<MedicalTechnician> employee = medicalTechnicianRepository.findById(empNum);
			if (employee.isPresent()) {
				List<Boolean> checkAlarm = new ArrayList<>();
				checkAlarm.add(employee.get().getIsNoticeAlarmOk());
				return checkAlarm;
			} else {
				throw new Exception("의료기사 알림 정보 조회 오류");
			}
		} else {
			throw new Exception("잘못된 직원번호 입니다");
		}
	}
}
