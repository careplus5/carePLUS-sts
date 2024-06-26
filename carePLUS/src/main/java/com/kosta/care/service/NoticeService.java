package com.kosta.care.service;

import java.util.List;

import com.kosta.care.dto.NoticesDto;
import com.kosta.care.util.PageInfo;

public interface NoticeService{
	
	String addNotice (NoticesDto noticesDto) throws Exception;
	List<NoticesDto> noticesListByPage(PageInfo pageInfo, String type, String word)throws Exception;
	NoticesDto noticesDetail(Long noticeNum) throws Exception;
	void noticesModify(NoticesDto noticesDto) throws Exception;
	void noticeDelete(Long noticeNum) throws Exception;
}
