package com.kosta.care.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.kosta.care.entity.Doctor;
import com.kosta.care.entity.Employee;
import com.kosta.care.entity.Nurse;

@Repository
public class EmployeeRepository{
	
	@Autowired
	private DoctorRepository docRepository;
	
	@Autowired
	private NurseRepository nurRepository;
	
	@Autowired
	private MedicalTechnicianRepository metRepository;
	
	
	@Autowired
	private EntityManager entityManager;
	
	
	
public Employee identifyJob(String username) {
	String identify = username.substring(0,2);
	Long id = Long.parseLong(username);
	if(identify.equals("11")) {
		System.out.println(identify);
		Employee emp = docRepository.findByDocNum(id);
		System.out.println(emp.getPassword());
		return docRepository.findByDocNum(id);
		
	} else if(identify.equals("12")) {
		System.out.println(identify);
		Employee emp = nurRepository.findByNurNum(id);
		System.out.println(emp.getPassword());
		return nurRepository.findByNurNum(id);
	} else if(identify.equals("13")){
		return metRepository.findByMetNum(id);
	} else {
		
		throw new UsernameNotFoundException("User not found with id:"+username);
	}
	
}

	
	public Doctor findByIdForDoctor(Long id) {
		return entityManager.find(Doctor.class, id);
	}
	public Nurse findByIdForNurse(Long id) {
		return entityManager.find(Nurse.class,id);
	}

	public void save(Employee emp) {
		
	}
	

}
