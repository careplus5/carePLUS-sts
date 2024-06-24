package com.kosta.care.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kosta.care.entity.MedicalTechnician;

@Repository
public interface MedicalTechnicianRepository extends JpaRepository<MedicalTechnician, Long> {

	MedicalTechnician findByMetNum(Long metNum);
	List<MedicalTechnician> findByJobNum(Long jobNum);
	
	Page<MedicalTechnician> findByMetDepartment2NameContains(String metDepartment2Name, PageRequest pageRequest);
	Page<MedicalTechnician> findByMetNumContains(Long metNum, PageRequest pageRequest);
	Page<MedicalTechnician> findByJobNumContains(String jobNum, PageRequest pageRequest);
	Page<MedicalTechnician> findByDepartmentNameContains(String jobName, PageRequest pageRequest);
	Page<MedicalTechnician> findByMetNameContains(String jobName, PageRequest pageRequest);
	
}
