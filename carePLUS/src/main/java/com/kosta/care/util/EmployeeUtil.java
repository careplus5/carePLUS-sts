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
	
	public Object chooseEmp(Employee emp) throws Exception {
		Long job = emp.getId();
		String jobString = job.toString();
		String findJob = jobString.substring(0,2);
		switch (findJob) {
	    case "11": return toDoc(emp);
	    case "12": return toNur(emp);
	    // case "13": return toAdm(emp);
	    // case "14": return toMet(emp);
	    default: throw new Exception("해당하는 직원 유형이 없습니다");
	}
		
	}
	
	
	

	public Nurse toNur(Employee emp) {
		Nurse nur = empRepository.findByIdForNurse(emp.getId());
		Nurse nurse = Nurse.builder()
				.nurNum(nur.getId())
				.profNum(nur.getProfNum())
				.departmentNum(nur.getDepartmentNum())
				.jobNum(nur.getJobNum())
				.nurName(nur.getNurName())
				.nurPassword(nur.getNurPassword())
				.nurTel(nur.getNurTel())
				.nurPosition(nur.getNurPosition())
				.nurEmail(nur.getNurEmail())
				.nurDepartment2Name(nur.getNurDepartment2Name())
				.build();
						
		return nurse;
	}
	
	public Doctor toDoc(Employee emp) {
		Doctor doc = empRepository.findByIdForDoctor(emp.getId());
		Doctor doctor = Doctor.builder()
				.docNum(doc.getDocNum())
				.profNum(doc.getProfNum())
				.departmentNum(doc.getDepartmentNum())
				.jobNum(doc.getJobNum())
				.docName(doc.getDocName())
				.docPassword(doc.getDocPassword())
				.docTel(doc.getDocTel())
				.docPosition(doc.getDocPosition())
				.docEmail(doc.getDocEmail())
				.build();
		return doctor;
	}
	
	// 여까지해보자 웅 그러자
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
