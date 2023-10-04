package com.bnw.beta.service.admin.notice;

import com.bnw.beta.domain.admin.dto.NoticeDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface NoticeService {
    //공지게시판 리스트
    List<NoticeDTO> noticeList(NoticeDTO noticeDTO);

    //공지게시판 글등록
    void insert(NoticeDTO noticeDTO, MultipartFile[] file, String member_id) throws IOException;

    //공지게시판 상세조회
    NoticeDTO detail(Long notice_no);

    //공지게시판 수정
    void update(Long notice_no, NoticeDTO noticeDTO, MultipartFile[] newFile);
}
