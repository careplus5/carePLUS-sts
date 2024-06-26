package com.kosta.care.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.kosta.care.entity.AdminHospital;
@Repository
public interface AdminHospitalRepository extends JpaRepository<AdminHospital,Long> {
	
	AdminHospital findByAdmNum(Long admNum);
	List<AdminHospital> findByJobNum (Long jobNum);
	
	Page<AdminHospital> findByAdmNumContains(Long admNum, PageRequest pageRequest);
	Page<AdminHospital> findByJobNumContains(String jobNum, PageRequest pageRequest);
	Page<AdminHospital> findByDepartmentNameContains(String departmentName, PageRequest pageRequest);
	Page<AdminHospital> findByAdmNameContains(String empName, PageRequest pageRequest);
	
	@Modifying
	@Query("UPDATE Admission a set a.admissionDischargeDate = :admissionDischargeDate, a.admissionDischargeOpinion =:admissionDischargeOpinion,  a.admissionStatus =:admissionStatus WHERE a.admissionNum = :admissionNum ")
	public void findByAdmissionNum(Long admissionNum);
}
