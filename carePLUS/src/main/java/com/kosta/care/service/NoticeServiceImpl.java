package com.kosta.care.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kosta.care.dto.NoticesDto;
import com.kosta.care.entity.Notices;
import com.kosta.care.repository.JobRepository;
import com.kosta.care.repository.NoticesRepository;
import com.kosta.care.util.PageInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

	private final NoticesRepository noticesRepository;
	private final AlarmService alarmService;
	private final JobRepository jobRepository;
	
	@Override
	public String addNotice(NoticesDto noticesDto) throws Exception {
		Notices notices = Notices.builder()
				.noticeCategory(noticesDto.getNoticeCategory())
				.noticeTitle(noticesDto.getNoticeTitle())
				.noticeContent(noticesDto.getNoticeContent())
				.noticeViewCount(0)
				.build();
				
		Long jobNum = Long.parseLong(noticesDto.getNoticeCategory());
		System.out.println(jobNum);
		if(jobNum==99) {
			alarmService.sendAlarmListByJobNum(11L, "공지사항", noticesDto.getNoticeTitle());
			alarmService.sendAlarmListByJobNum(12L, "공지사항", noticesDto.getNoticeTitle());
			alarmService.sendAlarmListByJobNum(13L, "공지사항", noticesDto.getNoticeTitle());
			alarmService.sendAlarmListByJobNum(14L, "공지사항", noticesDto.getNoticeTitle());
		}else {
			alarmService.sendAlarmListByJobNum(jobNum, "공지사항", noticesDto.getNoticeTitle());
		}
		
		noticesRepository.save(notices);
			
		
		return "작성완료";
	}

	@Override
	public List<NoticesDto> noticesListByPage(PageInfo pageInfo, String type, String word) throws Exception {
		PageRequest pageRequest = PageRequest.of(pageInfo.getCurPage() - 1, 5, Sort.by(Sort.Direction.DESC, "noticeNum"));
		Page<Notices> pages = null;
		// 목록 조회
		if (word == null || word.trim().equals("")) {
			pages = noticesRepository.findAll(pageRequest);
			System.out.println(pages);
		} else {
			if (type.equals("title")) {
				pages = noticesRepository.findByNoticeCategoryContains(word, pageRequest);
			} else if (type.equals("category")) {
				pages = noticesRepository.findByNoticeTitleContains(word, pageRequest);
			} else if (type.equals("content")) {
				pages = noticesRepository.findByNoticeContentContains(word, pageRequest);
			}
		}
		pageInfo.setAllPage(pages.getTotalPages());

		Integer startPage = (pageInfo.getCurPage() - 1) / 5 * 10 + 1;
		Integer endPage = Math.min(startPage + 5 - 1, pageInfo.getAllPage());
		pageInfo.setStartPage(startPage);
		pageInfo.setEndPage(endPage);

		List<NoticesDto> noticesDtoList = new ArrayList<>();
		for (Notices notices : pages.getContent()) {
			Long category = Long.parseLong(notices.getNoticeCategory());
			if(category==99L) {
				notices.setNoticeCategory("전체");
			}else {
				String jobName = jobRepository.findById(category).get().getJobName();
				notices.setNoticeCategory(jobName);	
			}
			noticesDtoList.add(notices.ToNoticesDto());
			
		}
		return noticesDtoList;
	}

	@Override
	public NoticesDto noticesDetail(Long noticeNum) throws Exception {
		Optional<Notices> oNotices = noticesRepository.findById(noticeNum);
		if(oNotices.isEmpty())
			throw new Exception("게시글 번호 오류");
		Notices notices = oNotices.get();
		notices.setNoticeViewCount(notices.getNoticeViewCount()+1);
		noticesRepository.save(notices);
		return oNotices.get().ToNoticesDto();
	}

	@Override
	public void noticesModify(NoticesDto noticesDto) throws Exception {
		
		Notices notices = Notices.builder()
				.noticeCategory(noticesDto.getNoticeCategory())
				.noticeTitle(noticesDto.getNoticeTitle())
				.noticeContent(noticesDto.getNoticeContent())
				.noticeViewCount(noticesDto.getNoticeViewCount())
				.build();
		
		Long jobNum = Long.parseLong(noticesDto.getNoticeCategory());
		if(jobNum==99) {
			alarmService.sendAlarmListByJobNum(11L, "공지사항", "[수정]"+noticesDto.getNoticeTitle());
			alarmService.sendAlarmListByJobNum(12L, "공지사항", "[수정]"+noticesDto.getNoticeTitle());
			alarmService.sendAlarmListByJobNum(13L, "공지사항", "[수정]"+noticesDto.getNoticeTitle());
			alarmService.sendAlarmListByJobNum(14L, "공지사항", "[수정]"+noticesDto.getNoticeTitle());
		}else {
			alarmService.sendAlarmListByJobNum(jobNum, "공지사항", "[수정]"+noticesDto.getNoticeTitle());
		}
	}

	@Override
	public void noticeDelete(Long noticeNum) throws Exception {
		Optional<Notices> oNotices = noticesRepository.findById(noticeNum);
		if(oNotices.isEmpty()) throw new Exception("게시글 번호 오류");
		noticesRepository.deleteById(noticeNum);
		
	}
}
