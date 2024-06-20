package com.kosta.care.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.care.entity.Disease;

public interface DiseaseRepository extends JpaRepository<Disease, Long> {
	List<Disease> findByDepartmentNumAndDiseaseNum(Long deptNum, String diseaseNum);
    List<Disease> findByDepartmentNumAndDiseaseName(Long deptNum, String diseaseName);
    List<Disease> findByDepartmentNum(Long deptNum);
}
