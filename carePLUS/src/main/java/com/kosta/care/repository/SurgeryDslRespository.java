package com.kosta.care.repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kosta.care.dto.SurgeryRequestDto;
import com.kosta.care.entity.QDepartment;
import com.kosta.care.entity.QDoctor;
import com.kosta.care.entity.QNurse;
import com.kosta.care.entity.QPatient;
import com.kosta.care.entity.QSurgery;
import com.kosta.care.entity.QSurgeryRequest;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
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
        		.join(nurse2).on(surgery.nurNum2.eq(nurse2.nurNum))
        		.join(department2).on(nurse2.departmentNum.eq(department2.departmentNum))
        		.where(surgery.surgeryNum.eq(surgeryNum))
        		.fetch();

        List<Tuple> result3 = jpaQueryFactory.select(surgery, nurse3.nurNum, nurse3.nurName, department3.departmentName)
        		.from(surgery)
        		.join(nurse3).on(surgery.nurNum3.eq(nurse3.nurNum))
        		.join(department3).on(nurse3.departmentNum.eq(department3.departmentNum))
        		.where(surgery.surgeryNum.eq(surgeryNum))
        		.fetch();
        
        List<Tuple> combineResult = new ArrayList<>();
        combineResult.addAll(result1);
        combineResult.addAll(result2);
        combineResult.addAll(result3);
		
		return combineResult;
	}
	
	public SurgeryRequestDto findSurgeryRequest(Long patNum) throws Exception {
		QSurgeryRequest surgeryRequest = QSurgeryRequest.surgeryRequest;
		QDepartment department = QDepartment.department;
		QDoctor doctor = QDoctor.doctor;
		
		return jpaQueryFactory.select(
			Projections.bean(SurgeryRequestDto.class,
					surgeryRequest.surgeryRequestNum,
					surgeryRequest.patNum,
					surgeryRequest.surPeriod,
					surgeryRequest.surReason,
					surgeryRequest.surDate,
					surgeryRequest.departmentNum,
					department.departmentName,
					surgeryRequest.docNum,
					doctor.docName
				))
			.from(surgeryRequest)
			.join(department)
			.on(surgeryRequest.departmentNum.eq(department.departmentNum))
			.join(doctor)
			.on(surgeryRequest.docNum.eq(doctor.docNum))
			.where(surgeryRequest.surgeryRequestAcpt.eq("wait").and(surgeryRequest.patNum.eq(patNum)))
			.fetchFirst();
	}

	public List<Map<String,Object>> findBySurNurseByOpDate(Long departmentNum, Date surDate) throws Exception {
		QSurgery surgery = QSurgery.surgery;
		List<Tuple> surNurList = jpaQueryFactory.select(surgery.nurNum1,surgery.nurNum2, surgery.nurNum3, surgery.surgeryDueStartTime)
				.from(surgery)
				.where(surgery.departmentNum.eq(departmentNum).and(surgery.surgeryDueDate.eq(surDate)))
				.fetch();
		List<Map<String,Object>> res = new ArrayList<>();
		for(Tuple tu : surNurList) {
			Long nur1 =  tu.get(0, Long.class);
			Long nur2 =  tu.get(1, Long.class);
			Long nur3 =  tu.get(2, Long.class);
			String time = tu.get(3, String.class);
			if(nur1!=null) {
				Map<String, Object> map = new HashMap<>();
				map.put("nurNum", nur1);
				map.put("time", time);
				res.add(map);
			}
			if(nur2!=null) {
				Map<String, Object> map = new HashMap<>();
				map.put("nurNum", nur2);
				map.put("time", time);
				res.add(map);
			}
			if(nur3!=null) {
				Map<String, Object> map = new HashMap<>();
				map.put("nurNum", nur3);
				map.put("time", time);
				res.add(map);
			}
		}
		return res;
	}

	
}
