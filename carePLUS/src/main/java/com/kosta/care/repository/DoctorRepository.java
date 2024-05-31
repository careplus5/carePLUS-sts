package com.kosta.care.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.care.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}
