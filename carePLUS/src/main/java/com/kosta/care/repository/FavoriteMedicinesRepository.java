package com.kosta.care.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.care.entity.FavoriteMedicines;

public interface FavoriteMedicinesRepository extends JpaRepository<FavoriteMedicines, Long> {

}
