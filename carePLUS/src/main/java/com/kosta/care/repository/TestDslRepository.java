package com.kosta.care.repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kosta.care.entity.QTest;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class TestDslRepository {
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	
	public List<Time> findByTestTimeByTestNameAndTestDate(String testName, Date testDate) {
		System.out.println(testName);
		System.out.println(testDate);
		QTest test = QTest.test;
		return jpaQueryFactory.select(test.testAppointmentTime)
            	.from(test)
				.where(test.testName.eq(testName)
						.and(test.testAppointmentDate.eq(testDate)
								.and(test.testStatus.eq("accept"))))
            	.fetch();
	}
}
