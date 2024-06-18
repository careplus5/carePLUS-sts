package com.kosta.care.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kosta.care.entity.PrescriptionDiary;

@Repository
public interface PrescriptionDiaryRepository extends JpaRepository<PrescriptionDiary, Long> {

	PrescriptionDiary save(PrescriptionDiary diary);
	
	@Modifying
    @Query("UPDATE PrescriptionDiary e SET e.prescriptionDiaryFre1 = :prescriptionDiaryFre1 WHERE e.prescriptionNum = :prescriptionNum")
    void updateDiary1(@Param("prescriptionNum") String prescriptionNum, @Param("prescriptionDiaryFre1") String prescriptionDiaryFre1);
	
	@Modifying
    @Query("UPDATE PrescriptionDiary e SET e.prescriptionDiaryFre2 = :prescriptionDiaryFre2 WHERE e.prescriptionDiaryNum = :prescriptionDiaryNum")
    void updateDiary2(@Param("prescriptionDiaryNum") String prescriptionDiaryNum, @Param("prescriptionDiaryFre2") String prescriptionDiaryFre2);

	@Modifying
    @Query("UPDATE PrescriptionDiary e SET e.prescriptionDiaryFre3 = :prescriptionDiaryFre3 WHERE e.prescriptionDiaryNum = :prescriptionDiaryNum")
    void updateDiary3(@Param("prescriptionDiaryNum") String prescriptionDiaryNum, @Param("prescriptionDiaryFre3") String prescriptionDiaryFre3);
}
