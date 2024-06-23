package com.kosta.care.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.care.entity.TestFile;

public interface TestFileRepository extends JpaRepository<TestFile, Long> {
	 List<TestFile> findByTest_testNum(Long testNum);
}
