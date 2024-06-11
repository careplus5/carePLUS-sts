package com.kosta.care.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.care.entity.Doctor;
import com.kosta.care.service.DoctorService;

@RestController
public class DoctorController {

	@Autowired
	private DoctorService doctorService;
	
	@GetMapping("/doctors")
	public ResponseEntity<List<Doctor>> doctorList(@RequestParam("departmentNum") Long departmentNum) {
		
		try {
			List<Doctor> doctorList = doctorService.doctorList(departmentNum);
			return new ResponseEntity<List<Doctor>>(doctorList,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Doctor>>(HttpStatus.BAD_REQUEST);
		}
	}
}
