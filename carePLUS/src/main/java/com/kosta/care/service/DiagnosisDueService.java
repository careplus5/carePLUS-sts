package com.kosta.care.service;

import java.util.List;

import com.kosta.care.dto.DiagnosisDueDto;

public interface DiagnosisDueService {
	
	List<DiagnosisDueDto> diagDueListByDocNum(Long docNum);
}
