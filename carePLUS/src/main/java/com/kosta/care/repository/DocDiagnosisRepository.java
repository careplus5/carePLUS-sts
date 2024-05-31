package com.kosta.care.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kosta.care.entity.DiagnosisDue;
import com.kosta.care.entity.QDiagnosisDue;
import com.kosta.care.entity.QDoctor;
import com.kosta.care.entity.QPatient;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class DocDiagnosisRepository {
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	
	public List<DiagnosisDue> findDiagDueListByDocNumAndDiagDueDate(Long docNum) {
		QDoctor doctor = QDoctor.doctor;
		QPatient patient = QPatient.patient;
		QDiagnosisDue diagnosisDue = QDiagnosisDue.diagnosisDue;
		
		return jpaQueryFactory.select(diagnosisDue)
					.from(diagnosisDue)
					.join(patient)
					.on(diagnosisDue.patNum.eq(patient.patNum))
					.join(doctor)
					.on(diagnosisDue.docNum.eq(doctor.docNum))
					.where(doctor.docNum.eq(docNum)
							.and(diagnosisDue.diagnosisDueDate.eq(Expressions.dateTemplate(Date.class, "CURDATE()"))))
					.fetch();
	}
	
}
