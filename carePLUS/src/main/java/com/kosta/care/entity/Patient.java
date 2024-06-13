package com.kosta.care.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.kosta.care.dto.PatientDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Patient {
	@Id
	private Long patNum;
	@Column
	private String patName;
	@Column
	private String patJumin;
	@Column
	private String patGender;
	@Column
	private String patAddress;
	@Column
	private String patTel;
	@Column
	private String patHeight;
	@Column
	private String patWeight;
	@Column
	private String patBloodType;
	@Column
	private String patHistory;
	
	public PatientDto patientDto() {
		return PatientDto.builder()
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
