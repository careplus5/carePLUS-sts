package com.kosta.care.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.care.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

	// 부서2 이름이 널이 아닌거
	List<Department> findByDepartment2NumIsNull() throws Exception;
}
