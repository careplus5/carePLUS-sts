package com.kosta.care.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kosta.care.dto.DiagnosisDueDto;
import com.kosta.care.entity.DiagnosisDue;
import com.kosta.care.repository.DiagnosisDueRepository;
import com.kosta.care.repository.DoctorRepository;
import com.kosta.care.repository.PatientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiagnosisDueServiceImpl implements DiagnosisDueService {
	
	private final DiagnosisDueRepository diagnosisDueRepository;
	private final PatientRepository patientRepository;
	private final DoctorRepository doctorRepository;
	
	@Override
	public List<DiagnosisDueDto> diagDueListByDocNum(Long docNum) {
		List<DiagnosisDue> diagDueList = diagnosisDueRepository.findByDocNum(docNum);
		System.out.println("서비스임플"+docNum);
		System.out.println("서비스임플"+diagDueList);
		if(diagDueList == null) return null;
		return diagDueList.stream()
						.map(DiagnosisDue::toDiagnosisDueDto)
						.collect(Collectors.toList());
	}

}
