package com.kosta.care.repository;

import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
=======
>>>>>>> branch 'main' of https://github.com/careplus5/carePLUS-sts.git
import org.springframework.stereotype.Repository;

import com.kosta.care.entity.AdminHospital;

@Repository
public interface AdminHospitalRepository extends JpaRepository<AdminHospital,Long> {
	AdminHospital findByAdmNum(Long admNum);
	
	@Modifying
	@Query("UPDATE Admission a set a.admissionDischargeDate = :admissionDischargeDate, a.admissionDischargeOpinion =:admissionDischargeOpinion,  a.admissionStatus =:admissionStatus WHERE a.admissionNum = :admissionNum ")
	public void findByAdmissionNum(Long admissionNum);
	

	AdminHospital findByAdmNum(Long admNum);
}
