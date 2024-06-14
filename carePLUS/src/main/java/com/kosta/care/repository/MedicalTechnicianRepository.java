package com.kosta.care.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kosta.care.entity.MedicalTechnician;

@Repository
public interface MedicalTechnicianRepository extends JpaRepository<MedicalTechnician, Long> {

	MedicalTechnician findByMetNum(Long metNum);
	List<MedicalTechnician> findByJobNum(Long jobNum);
}
