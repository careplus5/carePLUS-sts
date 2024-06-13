package com.kosta.care.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kosta.care.entity.AdminHospital;

@Repository
public interface AdminHospitalRepository extends JpaRepository<AdminHospital,Long> {

	AdminHospital findByAdmNum(Long admNum);
}
