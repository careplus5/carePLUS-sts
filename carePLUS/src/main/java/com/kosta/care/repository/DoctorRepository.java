package com.kosta.care.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kosta.care.entity.Doctor;
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
	
	Doctor findByDocNum(Long docNum);
//	 Page<Doctor> findBySubjectContains(String subject, PageRequest pageRequest);
//	 Page<Doctor> findByContentContains(String Content, PageRequest pageRequest);
//	 
}
