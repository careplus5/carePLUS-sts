package com.kosta.care.util;

import org.springframework.stereotype.Component;

import com.kosta.care.dto.EmployeeDto;
import com.kosta.care.entity.AdminHospital;
import com.kosta.care.entity.Doctor;
import com.kosta.care.entity.MedicalTechnician;
import com.kosta.care.entity.Nurse;

@Component
public class EmployeeUtil {
	public Object chooseEmp(EmployeeDto employeeDto) throws Exception {
		Long job = employeeDto.getJobNum();
		String jobString = job.toString();
		String findJob = jobString.substring(0,2);
		switch (findJob) {
	    case "11": return toDoc(employeeDto);
	    case "12": return toNur(employeeDto);
	    case "13": return toAdm(employeeDto);
	    case "14": return toMet(employeeDto);
	    default: throw new Exception("해당하는 직원 유형이 없습니다");
	}
		
	}

	public Nurse toNur(EmployeeDto employeeDto) {
		Nurse nurse = Nurse.builder()
				.nurNum(employeeDto.getEmpNum())
				.profNum(employeeDto.getProfNum())
				.departmentNum(employeeDto.getDepartmentNum())
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
	
	public Doctor toDoc(EmployeeDto employeeDto) {
		Doctor doctor = Doctor.builder()
				.docNum(employeeDto.getEmpNum())
				.profNum(employeeDto.getProfNum())
				.departmentNum(employeeDto.getDepartmentNum())
				.jobNum(employeeDto.getJobNum())
				.docName(employeeDto.getEmpName())
				.docPassword(employeeDto.getEmpPassword())
				.docTel(employeeDto.getEmpTel())
				.docPosition(employeeDto.getEmpPosition())
				.docEmail(employeeDto.getEmpEmail())
				.build();
		return doctor;
	}
	
	public AdminHospital toAdm(EmployeeDto employeeDto) {
		AdminHospital adm = AdminHospital.builder()
				.admNum(employeeDto.getEmpNum())
				.profNum(employeeDto.getProfNum())
				.departmentNum(employeeDto.getDepartmentNum())
				.jobNum(employeeDto.getJobNum())
				.admName(employeeDto.getEmpName())
				.admPassword(employeeDto.getEmpPassword())
				.admPosition(employeeDto.getEmpPosition())
				.admEmail(employeeDto.getEmpEmail())
				.build();
		return adm;
	}
	
	public MedicalTechnician toMet(EmployeeDto employeeDto) {
		MedicalTechnician met = MedicalTechnician.builder()
				.metNum(employeeDto.getEmpNum())
				.profNum(employeeDto.getProfNum())
				.departmentNum(employeeDto.getDepartmentNum())
				.jobNum(employeeDto.getJobNum())
				.metName(employeeDto.getEmpName())
				.metPassword(employeeDto.getEmpName())
				.metTel(employeeDto.getEmpTel())
				.metPosition(employeeDto.getEmpPosition())
				.metEmail(employeeDto.getEmpEmail())
				.metDepartment2Name(employeeDto.getDepartment2Name())
				.build();
		return met;
	}
	
}
