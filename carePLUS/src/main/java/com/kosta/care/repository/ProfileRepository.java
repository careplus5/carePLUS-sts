package com.kosta.care.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.care.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
