package com.kosta.care.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.kosta.care.entity.Doctor;
import com.kosta.care.entity.Employee;
import com.kosta.care.entity.MedicalTechnician;
import com.kosta.care.entity.Nurse;
import com.kosta.care.entity.QJob;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
public class EmployeeRepository{
	@Autowired
	private DoctorRepository docRepository;
	@Autowired
	private AdminHospitalRepository admHospitalRepository;
	@Autowired
	private NurseRepository nurRepository;
	
	@Autowired
	private MedicalTechnicianRepository metRepository;
	
	@Autowired
	private AdministorRepository adminRepository;
	@Autowired
	private AdminHospitalRepository admRepository;
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	
	
	
public Employee identifyJob(String username) {
	String identify = username.substring(0,2);
	Long id = Long.parseLong(username);
	if(identify.equals("11")) {
		System.out.println(identify);
		Employee emp = docRepository.findByDocNum(id);
		return docRepository.findByDocNum(id);
		
	} else if(identify.equals("12")) {
		System.out.println(identify);
		Employee emp = nurRepository.findByNurNum(id);
		return nurRepository.findByNurNum(id);

	} else if(identify.equals("13")) {
	return admHospitalRepository.findByAdmNum(id);
	}
	else if(identify.equals("14")){
	
		return metRepository.findByMetNum(id);
	} else if(identify.equals("13")){
		return admRepository.findByAdmNum(id);

	} else if(identify.equals("99")){
	return adminRepository.findByManNum(id);
	}
		 else throw new UsernameNotFoundException("User not found with id:"+username);
	
}

	
	public Doctor findByIdForDoctor(Long id) {
		return entityManager.find(Doctor.class, id);
	}
	public Nurse findByIdForNurse(Long id) {
		return entityManager.find(Nurse.class,id);
	}
	public MedicalTechnician findByIdForMet(Long id) {
		return entityManager.find(MedicalTechnician.class,id);
	}


	public void save(Employee emp) {
		
	}
	

}
