package com.kosta.care.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kosta.care.entity.Alarm;
import com.kosta.care.entity.QAlarm;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmployeeAlarmRepository {

	private final AlarmRepository alarmRepository;
	private final JPAQueryFactory jpaQueryFactory;

	public List<Alarm> AlarmListFindByEmpNumAndAlarmCheckFalse(Long empNum){
		QAlarm alarm = QAlarm.alarm;
		
		return jpaQueryFactory.selectFrom(alarm)
				.where(alarm.alarmCheck.eq(false).and(alarm.empNum.eq(empNum)))
				.fetch();
	}
}
