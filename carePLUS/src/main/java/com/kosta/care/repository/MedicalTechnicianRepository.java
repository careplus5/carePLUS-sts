package com.kosta.care.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kosta.care.entity.MedicalTechnician;

@Repository
public interface MedicalTechnicianRepository extends JpaRepository<MedicalTechnician, Long> {

	MedicalTechnician findByMetNum(Long metNum);
}
