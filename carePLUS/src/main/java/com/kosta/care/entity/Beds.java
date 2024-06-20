package com.kosta.care.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
public class Beds {
	@Id
	private Long bedsNum;
	@Column
	private Integer bedsDept;
	@Column
	private Integer bedsWard;
	@Column
	private Integer bedsRoom;
	@Column
	private Integer bedsBed;
	
	@Column
	private Boolean bedsIsUse;
}
