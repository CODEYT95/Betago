package com.bnw.beta.service.admin.notice;

import com.bnw.beta.domain.admin.dto.NoticeDTO;
import com.bnw.beta.domain.admin.dto.NoticeManagementDTO;
import com.bnw.beta.domain.common.paging.NoticePage;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface NoticeManagementService {
    //공지게시판 리스트
    NoticePage noticeManagementList(@Param("page") int page, @Param("size") int size, @Param("searchType") String searchType, @Param("keyword") String keyword);

    //총 게시글 개수 확인
    int listCnt(String searchType, String keyword);

    //공지게시판 삭제
    void delete(@Param("notice_no") List<Integer> notice_no);

    //드롭다운으로 삭제여부 Y/N
    String updateStatus(String notice_isshow, Long notice_no);

    //드롭다운으로 카테고리 변경
    String updateCategory(String notice_category, Long notice_no);

    //드롭다운으로 타입 변경
    String updateType(String type, Long notice_no);

    //드롭다운으로 예약일 변경
    String updateReservation(LocalDate notice_reservation, Long notice_no);
}
