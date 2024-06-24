package com.kosta.care.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.care.dto.NoticesDto;
import com.kosta.care.service.NoticeService;
import com.kosta.care.util.PageInfo;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class NoticeController {
	
	private final NoticeService noticeService;
		
	@PostMapping("/noticeWrite")
	public ResponseEntity<String> noticeWrite (@RequestBody NoticesDto noticesDto) {
		try {
			System.out.println(noticesDto);
			noticeService.addNotice(noticesDto);
			return new ResponseEntity<String>("공지사항 작성 성공", HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("공지사항 작성 실패", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/noticeList")
	public ResponseEntity<Map<String,Object>>noticeList(@RequestParam(name="page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name="type", required = false) String type,
			@RequestParam(name="word", required = false) String word){
		Map<String,Object> res = new HashMap<>();
		try {
			PageInfo pageInfo = PageInfo.builder().curPage(page).build();
			List<NoticesDto> noticeList = noticeService.noticesListByPage(pageInfo, type, word);
			res.put("noticeList", noticeList);
			res.put("pageInfo", pageInfo);
			return new ResponseEntity<Map<String,Object>>(res, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/noticeModify")
	public ResponseEntity<String> noticeModify(@RequestBody NoticesDto noticesDto) {
		try {
			System.out.println(noticesDto);
			noticeService.noticesModify(noticesDto);
			return new ResponseEntity<String>("수정 성공", HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("수정 실패", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("noticeDelete/")
	public ResponseEntity<String> noticeModify(@RequestParam("noticeNum") Long noticeNum){
		try {
			noticeService.noticeDelete(noticeNum);
			return new ResponseEntity<String>("공지사항 삭제", HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("공지사항 삭제 실패", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/noticeDetail")
	public ResponseEntity<NoticesDto> noticeDetail(@RequestParam("noticeNum") Long noticeNum){
		try {
			NoticesDto noticeDto = noticeService.noticesDetail(noticeNum);
			return new ResponseEntity<NoticesDto>(noticeDto,HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<NoticesDto>(HttpStatus.BAD_REQUEST);
		}
	}
}
