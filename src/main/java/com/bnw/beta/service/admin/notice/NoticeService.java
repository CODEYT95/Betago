package com.bnw.beta.service.admin.notice;
import com.bnw.beta.domain.admin.dto.NoticeDTO;
import com.bnw.beta.domain.common.paging.NoticePage;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Service
public interface NoticeService {
    //공지게시판 리스트
    NoticePage noticeList(@Param("page") int page, @Param("size") int size, @Param("searchType") String searchType, @Param("keyword") String keyword);
    //총 게시글 개수 확인
    int listCnt(String searchType, String keyword);
    //게시글 상단노출
    List<NoticeDTO> getTopNoticeList();
    //공지게시판 글등록
    void insert(NoticeDTO noticeDTO, MultipartFile[] file, String type, LocalDate timeWrite, HttpSession session) throws IOException, ParseException;
    //공지게시판 상세조회
    NoticeDTO detail(Long notice_no) ;
    //공지게시판 수정
    int update(Long notice_no, NoticeDTO noticeDTO, MultipartFile[] file, String type, LocalDate timeWrite) throws IOException;
    //공지게시판 삭제
    void delete(Long notice_no);

    //조회수증가
    void viewCnt(NoticeDTO noticeDTO);

}