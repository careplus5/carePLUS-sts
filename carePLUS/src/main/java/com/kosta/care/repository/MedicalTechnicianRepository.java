package com.kosta.care.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.care.entity.MedicalTechnician;

public interface MedicalTechnicianRepository extends JpaRepository<MedicalTechnician, Long> {

}
