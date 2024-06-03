package com.kosta.care.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kosta.care.entity.DiagnosisDue;
import com.kosta.care.entity.Disease;
import com.kosta.care.entity.QDepartment;
import com.kosta.care.entity.QDiagnosisDue;
import com.kosta.care.entity.QDisease;
import com.kosta.care.entity.QDocDiagnosis;
import com.kosta.care.entity.QDoctor;
import com.kosta.care.entity.QPatient;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;

@Repository
public class DiagnosisRepository {
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	
	@Autowired
	private EntityManager entityManager;
	
	//외래진료-대기환자목록
	public List<Tuple> findDiagDueListByDocNumAndDiagDueDate(Long docNum) {
		QDoctor doctor = QDoctor.doctor;
		QPatient patient = QPatient.patient;
		QDiagnosisDue diagnosisDue = QDiagnosisDue.diagnosisDue;
		QDocDiagnosis docDiagnosis = QDocDiagnosis.docDiagnosis;
		
		return jpaQueryFactory.select(diagnosisDue, patient.patName, docDiagnosis.docDiagnosisNum, docDiagnosis.docDiagnosisState)
					.from(diagnosisDue)
					.join(patient)
					.on(diagnosisDue.patNum.eq(patient.patNum))
					.join(doctor)
					.on(diagnosisDue.docNum.eq(doctor.docNum))
					.join(docDiagnosis)
					.on(diagnosisDue.patNum.eq(docDiagnosis.patNum))
					.where(docDiagnosis.docNum.eq(docNum)
							.and(diagnosisDue.diagnosisDueDate.eq(Expressions.dateTemplate(Date.class, "CURDATE()"))))
					.fetch();
	}

	//외래진료-환자접수정보
	public Tuple findDiagDueInfoByDocDiagNum(Long docDiagNum) {
		QPatient patient = QPatient.patient;
		QDiagnosisDue diagnosisDue = QDiagnosisDue.diagnosisDue;
		QDocDiagnosis docDiagnosis = QDocDiagnosis.docDiagnosis;
		
		return jpaQueryFactory.select(diagnosisDue, patient.patName, patient.patJumin, docDiagnosis.docDiagnosisState)
				.from(diagnosisDue)
				.join(patient)
				.on(diagnosisDue.patNum.eq(patient.patNum))
				.join(docDiagnosis)
				.on(diagnosisDue.patNum.eq(docDiagnosis.patNum))
				.where(docDiagnosis.docDiagnosisNum.eq(docDiagNum))
				.orderBy(diagnosisDue.diagnosisDueDate.desc())
	            .limit(1)
				.fetchOne();
	}
	
	//외래진료-병명리스트 조회(부서별)
	public List<Tuple> findDiseaseListByDeptNum(Long deptNum) {
		QDoctor doctor = QDoctor.doctor;
		QDisease disease = QDisease.disease;
		QDepartment department = QDepartment.department;
		
		return jpaQueryFactory.select(disease, department.departmentName)
				.from(disease)
				.join(department)
				.on(disease.departmentNum.eq(department.departmentNum))
				.where(disease.departmentNum.eq(deptNum))
				.fetch();
		
	}
	
	//외래진료-환자상태 변경
	@Transactional
	public void modifyDocDiagnosisState(Long docDiagNum, String newState) {
		QDocDiagnosis docDiagnosis = QDocDiagnosis.docDiagnosis;
		
		JPAUpdateClause updateClause = new JPAUpdateClause(entityManager, docDiagnosis);
		updateClause.where(docDiagnosis.docDiagnosisNum.eq(docDiagNum))
					.set(docDiagnosis.docDiagnosisState, newState)
					.execute();
				
	}
	
	
}
