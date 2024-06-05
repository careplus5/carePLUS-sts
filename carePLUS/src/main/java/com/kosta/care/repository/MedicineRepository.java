package com.kosta.care.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.care.entity.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, String> {
	List<Medicine> findByMedicineNum(String medicineNum);
    List<Medicine> findByMedicineEnNameContainingIgnoreCase(String medicineEnName);
    List<Medicine> findByMedicineKorNameContainingIgnoreCase(String medicineKorName);
}
