package com.kosta.care.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.care.entity.Admission;
import com.kosta.care.service.BedsService;

@RestController
public class WardController {
	
	 @Autowired
	    private BedsService bedsService;

	    @GetMapping("/wardsDept")
	    public List<Integer> getWardsByDepartment(@RequestParam Integer department) {
	        return bedsService.getWardsByDepartment(department);
	    }

	    @GetMapping("/wardsRooms")
	    public List<Integer> getRoomsByWard(@RequestParam Integer ward) {
	        return bedsService.getRoomsByWard(ward);
	    }

	    @GetMapping("/wardsBeds")
	    public List<Integer> getBedsByRoom(@RequestParam Integer room) {
	    	System.out.println(room);
	        return bedsService.getBedsByRoom(room);
	    }

	    @GetMapping("/wardsBed")
	    public ResponseEntity<Admission> getBedDetails( @RequestParam(required = false) String department,
	            @RequestParam(required = false) String ward,
	            @RequestParam(required = false) String room,
	            @RequestParam Long bed) {
	    	System.out.println(department);
	    	String bedsNum = department+ward+room+bed;
	    	System.out.println("bedsNum:"+bedsNum);
	    	Admission adm = bedsService.getBedDetails(Long.parseLong(bedsNum));
	    	  return ResponseEntity.ok(adm);
	    }
	    

}
