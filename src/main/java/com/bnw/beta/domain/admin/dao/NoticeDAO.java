package com.bnw.beta.domain.admin.dao;

import com.bnw.beta.domain.admin.dto.NoticeDTO;
import com.bnw.beta.domain.admin.dto.NoticeFileDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface NoticeDAO {

    //게시글 목록 조회
    List<NoticeDTO> noticeList(@Param("page") int page, @Param("size") int size,
                               @Param("searchType") String searchType, @Param("keyword") String keyword,List<NoticeDTO>topNoticeList );

    //게시글 상단노출
    List<NoticeDTO> noticeTop();

    //총 게시글 개수 확인
    int listCnt(@Param("searchType") String searchType, @Param("keyword") String keyword);

    //게시글 등록
    Long insert(NoticeDTO noticeDTO);

    //예약글 등록
    Long timeInsert(NoticeDTO noticeDTO);

    //파일 등록
    void fileUpload(NoticeFileDTO noticeFileDTO);

    //게시글 상세정보 조회
    NoticeDTO detail(Long notice_no);

    //파일 상세정보 조회
    List<NoticeFileDTO> getNoticeFiles(Long notice_no);

    //게시글 수정
    int update(NoticeDTO noticeDTO);

    //파일 삭제
    void deleteFile(Long notice_no);

   //게시글 삭제
    void delete(Long notice_no);

    //조회수 증가
    void viewCnt(NoticeDTO noticeDTO);

}
