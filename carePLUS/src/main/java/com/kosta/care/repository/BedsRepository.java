package com.kosta.care.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kosta.care.entity.Beds;

@Repository
public interface BedsRepository extends JpaRepository<Beds, Long> {
	

	  @Query("SELECT DISTINCT b.bedsWard FROM Beds b WHERE b.bedsDept = :dept")
	    List<Integer> findDistinctWardsByDepartment(@Param("dept") Integer dept);

	    @Query("SELECT DISTINCT b.bedsRoom FROM Beds b WHERE b.bedsWard = :ward")
	    List<Integer> findDistinctRoomsByWard(@Param("ward") Integer ward);
	    
	    @Query("SELECT DISTINCT b.bedsBed FROM Beds b WHERE b.bedsRoom = :room")
	    List<Integer> findDistinctBedsByRoom(@Param("room") Integer room);
	    
	    List<Beds> findByBedsDeptOrderByBedsWardAscBedsRoomAscBedsNum(Long deptNum);
	    
}
