package com.kosta.care.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kosta.care.entity.QDepartment;
import com.kosta.care.entity.QDoctor;
import com.kosta.care.entity.QNurse;
import com.kosta.care.entity.QPatient;
import com.kosta.care.entity.QSurgery;
import com.kosta.care.entity.QSurgeryRequest;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class SurgeryDslRespository {
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	
	//수술진료-수술환자목록
	public List<Tuple> findSurgeryListByDocNum(Long docNum) {
		QPatient patient = QPatient.patient;
		QSurgery surgery = QSurgery.surgery;
		
		return jpaQueryFactory.select(surgery, patient)
				.from(surgery)
				.join(patient).on(surgery.patNum.eq(patient.patNum))
				.where(surgery.docNum.eq(docNum))
				.orderBy(
						new CaseBuilder()
							.when(surgery.surgeryState.eq("ing")).then(1)
							.when(surgery.surgeryState.eq("end")).then(3)
							.otherwise(2).asc(),
							surgery.surgeryDueDate.asc(),
							surgery.surgeryStartTime.asc()
				)
				.fetch();
	}
	
	//수술진료-수술환자정보, 수술정보
	public Tuple findSurgeryInfoBySurgeryNum(Long surgeryNum) {
		QSurgery surgery = QSurgery.surgery;
		QPatient patient = QPatient.patient;
		QSurgeryRequest surgeryRequest = QSurgeryRequest.surgeryRequest;
		QDoctor doctor = QDoctor.doctor;
		
		return jpaQueryFactory.select(surgery, patient, surgeryRequest, doctor)
				.from(surgery)
				.join(patient).on(surgery.patNum.eq(patient.patNum))
				.join(surgeryRequest).on(surgery.surgeryRequestNum.eq(surgeryRequest.surgeryRequestNum))
				.join(doctor).on(surgery.docNum.eq(doctor.docNum))
				.where(surgery.surgeryNum.eq(surgeryNum))
				.fetchOne();
	}
	
	//수술진료-수술참여 간호사 목록 조회
	public List<Tuple> findSurNurseListBySurgeryNum(Long surgeryNum) {
		QSurgery surgery = QSurgery.surgery;
		QNurse nurse1 = new QNurse("nurse1");
        QNurse nurse2 = new QNurse("nurse2");
        QNurse nurse3 = new QNurse("nurse3");
        QDepartment department1 = new QDepartment("department1");
        QDepartment department2 = new QDepartment("department2");
        QDepartment department3 = new QDepartment("department3");
        
        List<Tuple> result1 = jpaQueryFactory.select(surgery, nurse1.nurNum, nurse1.nurName, department1.departmentName)
        		.from(surgery)
        		.join(nurse1).on(surgery.nurNum1.eq(nurse1.nurNum))
        		.join(department1).on(nurse1.departmentNum.eq(department1.departmentNum))
        		.where(surgery.surgeryNum.eq(surgeryNum))
        		.fetch();
        
        List<Tuple> result2 = jpaQueryFactory.select(surgery, nurse2.nurNum, nurse2.nurName, department2.departmentName)
        		.from(surgery)
        		.join(nurse2).on(surgery.nurNum1.eq(nurse2.nurNum))
        		.join(department2).on(nurse2.departmentNum.eq(department2.departmentNum))
        		.where(surgery.surgeryNum.eq(surgeryNum))
        		.fetch();

        List<Tuple> result3 = jpaQueryFactory.select(surgery, nurse3.nurNum, nurse3.nurName, department3.departmentName)
        		.from(surgery)
        		.join(nurse3).on(surgery.nurNum1.eq(nurse3.nurNum))
        		.join(department3).on(nurse3.departmentNum.eq(department3.departmentNum))
        		.where(surgery.surgeryNum.eq(surgeryNum))
        		.fetch();
        
        List<Tuple> combineResult = new ArrayList<>();
        combineResult.addAll(result1);
        combineResult.addAll(result2);
        combineResult.addAll(result3);
		
		return combineResult;
	}
	
}
