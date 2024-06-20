package com.kosta.care.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.kosta.care.dto.NoticesDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notices {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long noticeNum;
	@Column
	private String noticeCategory;
	@Column
	private String noticeTitle;
	@Column
	private String noticeContent;
	@Column
	@CreationTimestamp
	private Date noticeWriteDate;
	@Column
	@ColumnDefault("0")
	private Integer noticeViewCount;
	
	public NoticesDto ToNoticesDto() {
		
		return NoticesDto.builder()
				.noticeNum(noticeNum)
				.noticeCategory(noticeCategory)
				.noticeTitle(noticeTitle)
				.noticeContent(noticeContent)
				.noticeWriteDate(noticeWriteDate)
				.noticeViewCount(noticeViewCount)
				.build();
	}
}
