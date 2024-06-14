package com.kosta.care.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmDto {
	private Long AlarmNum;
	private String category;
	private String content;
	private Boolean isCheck;
	private String type;
}
