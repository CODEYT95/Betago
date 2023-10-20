package com.bnw.beta.domain.admin.dao;

import com.bnw.beta.domain.admin.dto.NoticeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FaqDAO {

    //faq 메인
    List<NoticeDTO> faqAll();

    //리스트
    List<NoticeDTO> faqList(@Param("page") int page, @Param("size") int size,
                            @Param("searchType") String searchType, @Param("keyword") String keyword);

    //총 게시글 개수 확인
    int listCnt(@Param("searchType") String searchType, @Param("keyword") String keyword);

    //게시글 수정
    int update(NoticeDTO noticeDTO);

    //게시글 삭제
    void delete(Long notice_no);
}
