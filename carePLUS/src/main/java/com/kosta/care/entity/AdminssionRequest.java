package com.kosta.care.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminssionRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer adminssionDueNum;
	@Column
	private Integer patNum;
	@Column
	private Integer diagNum;
	@Column
	private Integer docNum;
	@Column
	private Integer jobNum;
	@Column
	private String adminssionDuePeriod;
	@Column
	private String adminssionDueReason;
}
