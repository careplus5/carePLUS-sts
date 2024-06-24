package com.kosta.care.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kosta.care.dto.EmployeeDto;
import com.kosta.care.entity.AdminHospital;
import com.kosta.care.entity.Doctor;
import com.kosta.care.entity.Employee;
import com.kosta.care.entity.MedicalTechnician;
import com.kosta.care.entity.Nurse;
import com.kosta.care.repository.EmployeeRepository;

@Component
public class EmployeeUtil {
	@Autowired
	private EmployeeRepository empRepository;
	
	
	public Object chooseEmpDto(EmployeeDto empDto) throws Exception{
		Long job = empDto.getEmpNum();
		String jobString = job.toString();
		String findJob = jobString.substring(0,2);
		switch (findJob) {
		    case "11": return DtoToDoc(empDto);
		    case "12": return DtoToNur(empDto);
		    case "13": return DtoToAdm(empDto);
		    case "14": return DtoToMet(empDto);
		    default: throw new Exception("해당하는 직원 유형이 없습니다");
		}
	}
	
	public EmployeeDto DocToEmpDto(Doctor doctor) throws Exception{
		EmployeeDto empDto = EmployeeDto.builder()
				.empNum(doctor.getDocNum())
				.profNum(doctor.getProfNum())
				.departmentNum(doctor.getDepartmentNum())
				.departmentName(doctor.getDepartmentName())
				.jobNum(doctor.getJobNum())
				.jobName("의사")
				.empPosition(doctor.getDocPosition())
				.empName(doctor.getDocName())
				.empTel(doctor.getDocTel())
				.empEmail(doctor.getDocEmail())
				.build();
		return empDto;
	}
	
	public EmployeeDto NurToEmpDto(Nurse nurse) throws Exception{
		if(nurse.getNurPosition().equals("1")) {
			nurse.setNurPosition("진료");
		}else if(nurse.getNurPosition().equals("2")) {
			nurse.setNurPosition("입원");
		}else if(nurse.getNurPosition().equals("3")) {
			nurse.setNurPosition("수술");
		}
		EmployeeDto empDto = EmployeeDto.builder()
				.empNum(nurse.getNurNum())
				.profNum(nurse.getProfNum())
				.departmentNum(nurse.getDepartmentNum())
				.departmentName(nurse.getDepartmentName())
				.jobNum(nurse.getJobNum())
				.jobName("간호사")
				.empPosition(nurse.getNurPosition())
				.empName(nurse.getNurName())
				.empTel(nurse.getNurTel())
				.empEmail(nurse.getNurEmail())
				.build();
		return empDto;
	}

	public EmployeeDto AdmToEmpDto(AdminHospital adminHospital) throws Exception{
		EmployeeDto empDto = EmployeeDto.builder()
				.empNum(adminHospital.getAdmNum())
				.profNum(adminHospital.getProfNum())
				.departmentNum(adminHospital.getDepartmentNum())
				.departmentName(adminHospital.getDepartmentName())
				.jobNum(adminHospital.getJobNum())
				.jobName("원무과")
				.empPosition(adminHospital.getAdmPosition())
				.empName(adminHospital.getAdmName())
				.empTel(adminHospital.getAdmTel())
				.empEmail(adminHospital.getAdmEmail())
				.build();
		return empDto;
	}
	
	public EmployeeDto MetToEmpDto(MedicalTechnician medicalTechnician) throws Exception{
		EmployeeDto empDto = EmployeeDto.builder()
				.empNum(medicalTechnician.getMetNum())
				.profNum(medicalTechnician.getProfNum())
				.departmentNum(medicalTechnician.getDepartmentNum())
				.jobNum(medicalTechnician.getJobNum())
				.departmentName(medicalTechnician.getDepartmentName())
				.department2Name(medicalTechnician.getMetDepartment2Name())
				.jobName("의료기사")
				.empPosition(medicalTechnician.getMetPosition())
				.empName(medicalTechnician.getMetName())
				.empTel(medicalTechnician.getMetTel())
				.empEmail(medicalTechnician.getMetEmail())
				.build();
		return empDto;
	}
	
	public Doctor DtoToDoc(EmployeeDto employeeDto) {
		Doctor doctor = Doctor.builder()
				.docNum(employeeDto.getEmpNum())
				.profNum(employeeDto.getProfNum())
				.departmentNum(employeeDto.getDepartmentNum())
				.departmentName(employeeDto.getDepartmentName())
				.jobNum(employeeDto.getJobNum())
				.docName(employeeDto.getEmpName())
				.docPassword(employeeDto.getEmpPassword())
				.docTel(employeeDto.getEmpTel())
				.docPosition(employeeDto.getEmpPosition())
				.docEmail(employeeDto.getEmpEmail())
				.build();
		return doctor;
	}
	
	public Nurse DtoToNur(EmployeeDto employeeDto) {
		if(employeeDto.getEmpPosition().equals("진료")) {
			employeeDto.setEmpPosition("1");
		}else if(employeeDto.getEmpPosition().equals("입원")) {
			employeeDto.setEmpPosition("2");
		}else if(employeeDto.getEmpPosition().equals("수술")) {
			employeeDto.setEmpPosition("3");
		}
		Nurse nurse = Nurse.builder()
				.nurNum(employeeDto.getEmpNum())
				.profNum(employeeDto.getProfNum())
				.departmentNum(employeeDto.getDepartmentNum())
				.departmentName(employeeDto.getDepartmentName())
				.jobNum(employeeDto.getJobNum())
				.nurName(employeeDto.getEmpName())
				.nurPassword(employeeDto.getEmpPassword())
				.nurTel(employeeDto.getEmpTel())
				.nurPosition(employeeDto.getEmpPosition())
				.nurEmail(employeeDto.getEmpEmail())
				.nurDepartment2Name(employeeDto.getDepartment2Name())
				.build();
		return nurse;
	}

	public AdminHospital DtoToAdm(EmployeeDto employeeDto) {
		AdminHospital adm = AdminHospital.builder()
				.admNum(employeeDto.getEmpNum())
				.profNum(employeeDto.getProfNum())
				.departmentNum(employeeDto.getDepartmentNum())
				.departmentName(employeeDto.getDepartmentName())
				.jobNum(employeeDto.getJobNum())
				.admName(employeeDto.getEmpName())
				.admPassword(employeeDto.getEmpPassword())
				.admTel(employeeDto.getEmpTel())
				.admPosition(employeeDto.getEmpPosition())
				.admEmail(employeeDto.getEmpEmail())
				.build();
		return adm;
	}
	
	public MedicalTechnician DtoToMet(EmployeeDto employeeDto) {
		MedicalTechnician met = MedicalTechnician.builder()
				.metNum(employeeDto.getEmpNum())
				.profNum(employeeDto.getProfNum())
				.departmentNum(employeeDto.getDepartmentNum())
				.departmentName(employeeDto.getDepartmentName())
				.jobNum(employeeDto.getJobNum())
				.metName(employeeDto.getEmpName())
				.metPassword(employeeDto.getEmpPassword())
				.metTel(employeeDto.getEmpTel())
				.metPosition(employeeDto.getEmpPosition())
				.metEmail(employeeDto.getEmpEmail())
				.metDepartment2Name(employeeDto.getDepartment2Name())
				.build();
		return met;
	}
	
	public Doctor modifyDtoToDoc(EmployeeDto employeeDto, EmployeeDto beforeEmployeeDto) {
	    Doctor doctor = Doctor.builder()
	            .docNum(employeeDto.getEmpNum())
	            .profNum(employeeDto.getProfNum())
	            .departmentNum(employeeDto.getDepartmentNum())
	            .departmentName(employeeDto.getDepartmentName())
	            .jobNum(employeeDto.getJobNum())
	            .docName(employeeDto.getEmpName())
	            .docPassword(employeeDto.getEmpPassword())
	            .docTel(employeeDto.getEmpTel())
	            .docPosition(employeeDto.getEmpPosition())
	            .docEmail(employeeDto.getEmpEmail())
	            .isDiagnosAlarmOk(beforeEmployeeDto.getIsDiagnosAlarmOk())
	            .isNoticeAlarmOk(beforeEmployeeDto.getIsNoticeAlarmOk()) 
	            .isSurgeryAlarmOk(beforeEmployeeDto.getIsSurgeryAlarmOk())
	            .build();

	    return doctor;
	}

	public Nurse modifyDtoToNur(EmployeeDto employeeDto, EmployeeDto beforeEmployeeDto) {
	    // 직위에 따른 직종 설정
	    if (employeeDto.getEmpPosition().equals("진료")) {
	        employeeDto.setEmpPosition("1");
	    } else if (employeeDto.getEmpPosition().equals("입원")) {
	        employeeDto.setEmpPosition("2");
	    } else if (employeeDto.getEmpPosition().equals("수술")) {
	        employeeDto.setEmpPosition("3");
	    }

	    Nurse nurse = Nurse.builder()
	            .nurNum(employeeDto.getEmpNum())
	            .profNum(employeeDto.getProfNum())
	            .departmentNum(employeeDto.getDepartmentNum())
	            .departmentName(employeeDto.getDepartmentName())
	            .jobNum(employeeDto.getJobNum())
	            .nurName(employeeDto.getEmpName())
	            .nurPassword(employeeDto.getEmpPassword())
	            .nurTel(employeeDto.getEmpTel())
	            .nurPosition(employeeDto.getEmpPosition())
	            .nurEmail(employeeDto.getEmpEmail())
	            .isAdmissionAlarmOk(beforeEmployeeDto.getIsAdmissionAlarmOk()) // 간호사에 맞는 AlarmOk 속성 설정
	            .isNoticeAlarmOk(beforeEmployeeDto.getIsNoticeAlarmOk()) // 간호사에 맞는 AlarmOk 속성 설정
	            .isRequestAlarmOk(beforeEmployeeDto.getIsRequestAlarmOk()) // 간호사에 맞는 AlarmOk 속성 설정
	            .isSurgeryAlarmOk(beforeEmployeeDto.getIsSurgeryAlarmOk()) // 간호사에 맞는 AlarmOk 속성 설정
	            .build();

	    return nurse;
	}

	public AdminHospital modifyDtoToAdm(EmployeeDto employeeDto, EmployeeDto beforeEmployeeDto) {
	    AdminHospital adm = AdminHospital.builder()
	            .admNum(employeeDto.getEmpNum())
	            .profNum(employeeDto.getProfNum())
	            .departmentNum(employeeDto.getDepartmentNum())
	            .departmentName(employeeDto.getDepartmentName())
	            .jobNum(employeeDto.getJobNum())
	            .admName(employeeDto.getEmpName())
	            .admPassword(employeeDto.getEmpPassword())
	            .admTel(employeeDto.getEmpTel())
	            .admPosition(employeeDto.getEmpPosition())
	            .admEmail(employeeDto.getEmpEmail())
	            .isDischargeAlarmOk(beforeEmployeeDto.getIsDischargeAlarmOk()) // 병원 관리자에 맞는 AlarmOk 속성 설정
	            .isNoticeAlarmOk(beforeEmployeeDto.getIsNoticeAlarmOk()) // 병원 관리자에 맞는 AlarmOk 속성 설정
	            .isPrescriptionAlarmOk(beforeEmployeeDto.getIsPrescriptionAlarmOk()) // 병원 관리자에 맞는 AlarmOk 속성 설정
	            .build();

	    return adm;
	}

	public MedicalTechnician modifyDtoToMet(EmployeeDto employeeDto, EmployeeDto beforeEmployeeDto) {
	    MedicalTechnician met = MedicalTechnician.builder()
	            .metNum(employeeDto.getEmpNum())
	            .profNum(employeeDto.getProfNum())
	            .departmentNum(employeeDto.getDepartmentNum())
	            .departmentName(employeeDto.getDepartmentName())
	            .jobNum(employeeDto.getJobNum())
	            .metName(employeeDto.getEmpName())
	            .metPassword(employeeDto.getEmpPassword())
	            .metTel(employeeDto.getEmpTel())
	            .metPosition(employeeDto.getEmpPosition())
	            .metEmail(employeeDto.getEmpEmail())
	            .isNoticeAlarmOk(beforeEmployeeDto.getIsNoticeAlarmOk()) // 의료 기술자에 맞는 AlarmOk 속성 설정
	            .isTestAlarmOk(beforeEmployeeDto.getIsTestAlarmOk()) // 의료 기술자에 맞는 AlarmOk 속성 설정
	            .metDepartment2Name(employeeDto.getDepartment2Name()) // 예시로 추가된 속성 설정
	            .build();

	    return met;
	}
	
}
