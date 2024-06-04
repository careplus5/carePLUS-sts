package com.kosta.care.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.care.entity.Alarm;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

}
