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
public class FavoriteMedicines {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long FavoriteMedicinesNum;
	@Column
	private String medicineNum;
	@Column
	private Long docNum;
}
