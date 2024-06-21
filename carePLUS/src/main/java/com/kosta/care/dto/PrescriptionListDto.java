package com.kosta.care.dto;

import java.util.ArrayList;
import java.util.List;

import com.kosta.care.entity.Prescription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class PrescriptionListDto {

	    private List<PrescriptionDto> prescriptionDtos;

	    public PrescriptionListDto() {
	        this.prescriptionDtos = new ArrayList<>();
	    }

	    public PrescriptionListDto addPrescriptionDto(PrescriptionDto prescriptionDto) {
	        this.prescriptionDtos.add(prescriptionDto);
	        return this;
	    }

	    public List<Prescription> build() {
	        List<Prescription> prescriptions = new ArrayList<>();
	        for (PrescriptionDto dto : prescriptionDtos) {
	        	System.out.println(dto.toString()+"에 관한 내용입니다 ");
	            Prescription prescription = Prescription.builder()
	                    .prescriptionNum(dto.getPrescriptionNum())
	                    .medicineNum(dto.getMedicineNum())
	                    .patNum(dto.getPatNum())
	                    .docNum(dto.getDocNum())
	                    .prescriptionDosage(dto.getPreDosage())
	                    .prescriptionDosageTimes(dto.getPreDosageTimes())
	                    .prescriptionDosageTotal(dto.getPreDosageTotal())
	                    .prescriptionHowTake(dto.getPreHowTake())
	                    .prescriptionDate(dto.getPrescriptionDate())
	                    .build();
	            prescriptions.add(prescription);
	        }
	        return prescriptions;

}
	}
