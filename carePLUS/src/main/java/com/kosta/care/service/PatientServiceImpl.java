package com.kosta.care.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.kosta.care.dto.PatientDto;
import com.kosta.care.entity.Patient;
import com.kosta.care.repository.PatientRepository;
import com.kosta.care.util.PageInfo;

@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientRepository patientRepository;

	// 환자 번호로 환자 조회
	@Override
	public Optional<Patient> getPatientById(Long patNum) throws Exception {

		return patientRepository.findById(patNum);

	}

	// 모든 환자 리스트 조회 
	public List<PatientDto> getAllPatientSearch(String type, String word) throws Exception {
		List<PatientDto> patientDtoList = new ArrayList<>();

		// 목록 조회
        if (type == null || word == null || word.trim().equals("")) {
            throw new Exception("검색어나 타입이 유효하지 않습니다.");
        } else {
            List<Patient> patientList = new ArrayList<>();
            try {
                if (type.equals("patNum")) {
                    Long patNum = Long.parseLong(word);
                    patientList = patientRepository.findByPatNum(patNum);
                } else if (type.equals("patName")){
                    patientList = patientRepository.findByPatNameContains(word);
                } else if (type.equals("patJumin")) {
                    patientList = patientRepository.findByPatJuminContains(word);
                }

                // Patient 엔티티를 PatientDto로 변환하여 리스트에 추가
                for (Patient patient : patientList) {
                    PatientDto patientDto = new PatientDto();
                    patientDto.setPatNum(patient.getPatNum());
                    patientDto.setPatName(patient.getPatName());
                    patientDto.setPatJumin(patient.getPatJumin());
                    patientDto.setPatGender(patient.getPatGender());
                    patientDto.setPatAddress(patient.getPatAddress());
                    patientDto.setPatTel(patient.getPatTel());
                    patientDto.setPatHeight(patient.getPatHeight());
                    patientDto.setPatWeight(patient.getPatWeight());
                    patientDto.setPatBloodType(patient.getPatBloodType());
                    patientDto.setPatHistory(patient.getPatHistory());

                    patientDtoList.add(patientDto);
                }
            } catch (NumberFormatException e) {
                throw new Exception("환자 번호는 숫자여야 합니다.", e);
            }
        }

        return patientDtoList;
    }



	@Override
	public Patient joinPatient(PatientDto patientDto) throws Exception {

		return patientRepository.save(patientDto.patientEntity());
	}

}
