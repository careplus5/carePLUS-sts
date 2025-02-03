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

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

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
	private EntityManager entityManager;
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	
	
	
public Employee identifyJob(String username) {
	char firstChar = username.charAt(0);
	char secondChar = username.charAt(1);

	String identify = "" + firstChar + secondChar;
	Long id = Long.parseLong(username);

	Map<String, Function<Long, Employee>> repositoryMap = new HashMap<>();
	repositoryMap.put("11", docRepository::findByDocNum);
	repositoryMap.put("12", nurRepository::findByNurNum);
	repositoryMap.put("13", admHospitalRepository::findByAdmNum);
	repositoryMap.put("14", metRepository::findByMetNum);
	repositoryMap.put("99", adminRepository::findByManNum);

	if (repositoryMap.containsKey(identify)) {
		return repositoryMap.get(identify).apply(id);
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
