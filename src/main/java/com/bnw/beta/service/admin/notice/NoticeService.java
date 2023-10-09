package com.bnw.beta.service.admin.notice;

import com.bnw.beta.domain.admin.dto.NoticeDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public interface NoticeService {
    //공지게시판 리스트
    List<NoticeDTO> noticeList( @Param("page") int page, @Param("size") int size,@Param("searchType") String searchType, @Param("keyword") String keyword);

    //총 게시글 개수 확인
    int listCnt(String searchType, String keyword);

    //공지게시판 글등록
    void insert(NoticeDTO noticeDTO, MultipartFile[] file, String member_id) throws IOException;

    //공지게시판 상세조회
    NoticeDTO detail(Long notice_no) ;

    //공지게시판 수정
    NoticeDTO update(Long notice_no, NoticeDTO noticeDTO, MultipartFile[] file) throws IOException;

    //공지게시판 삭제
    void delete(Long notice_no);

}
