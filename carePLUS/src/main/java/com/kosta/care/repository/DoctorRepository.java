package com.kosta.care.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.care.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
	 
}
