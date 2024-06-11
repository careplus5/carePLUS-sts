package com.kosta.care.service;

import java.util.List;

import com.kosta.care.entity.Department;


public interface DepartmentService {

	List<Department> departmentList() throws Exception;
}
