package com.kosta.care.dto;

import java.util.List;

import com.kosta.care.entity.Admission;
import com.kosta.care.entity.DocDiagnosis;
import com.kosta.care.entity.Patient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientDto {
	private Long patNum;
	private String patName;
	private String patJumin;
	private String patGender;
	private String patAddress;
	private String patTel;
	private String patHeight;
	private String patWeight;
	private String patBloodType;
	private String patHistory;
	
	// 필요해서 추가 했음 - 김동횬 
	private List<Admission> admissionList; // 입원 정보 리스트
    private List<DocDiagnosis> docDiagnosisList; // 진료 일정 리스트
	
	public Patient patientEntity() {
		return Patient.builder()
					.patNum(patNum)
					.patName(patName)
					.patJumin(patJumin)
					.patGender(patGender)
					.patAddress(patAddress)
					.patTel(patTel)
					.patHeight(patHeight)
					.patWeight(patWeight)
					.patBloodType(patBloodType)
					.patHistory(patHistory)
					.build();
		
	}
}
