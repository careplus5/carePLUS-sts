package com.kosta.care.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import com.kosta.care.entity.Notices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticesDto {
	private Long noticeNum;
	private String noticeCategory;
	private String noticeTitle;
	private String noticeContent;
	private Date noticeWriteDate;
	private Integer noticeViewCount;
	
	public Notices ToNotices() {
		
		return Notices.builder()
				.noticeNum(noticeNum)
				.noticeCategory(noticeCategory)
				.noticeTitle(noticeTitle)
				.noticeContent(noticeContent)
				.noticeWriteDate(noticeWriteDate)
				.noticeViewCount(noticeViewCount)
				.build();
	}
	
}
