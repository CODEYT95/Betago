package com.bnw.beta.service.admin.notice;

import com.bnw.beta.domain.admin.dto.NoticeDTO;
import com.bnw.beta.domain.common.paging.NoticePage;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoticeManagementService {
    //공지게시판 리스트
    NoticePage noticeManagementList(@Param("page") int page, @Param("size") int size, @Param("searchType") String searchType, @Param("keyword") String keyword);

    //총 게시글 개수 확인
    int listCnt(String searchType, String keyword);

    //공지게시판 삭제
    void delete(@Param("notice_no") List<Long> notice_no);

    //공지게시판 삭제
    void delete1(@Param("notice_no") String notice_no);
}
