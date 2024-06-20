package com.kosta.care.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.care.entity.Admission;
import com.kosta.care.repository.AdmissionRepository;
import com.kosta.care.repository.BedsRepository;

@Service
public class BedsServiceImpl implements BedsService {
	
	 @Autowired
	    private BedsRepository bedsRepository;
	 

	 @Autowired
	    private AdmissionRepository admRepository;

	 @Override
	    public List<Integer> getWardsByDepartment(Integer department) {
	        return bedsRepository.findDistinctWardsByDepartment(department);
	    }

	    @Override
	    public List<Integer> getRoomsByWard(Integer ward) {
	        return bedsRepository.findDistinctRoomsByWard(ward);
	    }

	    @Override
	    public List<Integer> getBedsByRoom(Integer room) {
	        return bedsRepository.findDistinctBedsByRoom(room);
	    }

	    @Override
	    public Admission getBedDetails(Long bedsNum) {
	    	Admission adm = admRepository.findByBedsNum(bedsNum);
	    	Long errorNum = 444L;
	    		  if (adm==null) {
	    	            adm = new Admission();
	    	            adm.setPatNum(null);
	    	            adm.setAdmissionNum(errorNum);
	    	            adm.setAdmissionDate(null);
	    	            adm.setAdmissionDischargeDueDate(null);
	    	            adm.setBedsNum(bedsNum);
	    	            System.out.println("adm의 내용이 업습니다.");
	    }  return adm;}
}
