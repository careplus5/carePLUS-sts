package com.kosta.care.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.care.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
