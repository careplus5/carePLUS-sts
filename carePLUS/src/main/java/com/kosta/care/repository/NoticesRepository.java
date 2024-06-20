package com.kosta.care.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.care.entity.Notices;

public interface NoticesRepository extends JpaRepository<Notices, Long> {
	Page<Notices> findByNoticeCategoryContains(String noticeCategory, PageRequest pageReqeust) throws Exception;
	Page<Notices> findByNoticeTitleContains(String noticeTitle, PageRequest pageRequest) throws Exception;
	Page<Notices> findByNoticeContentContains(String content, PageRequest pageRequest) throws Exception;
}
