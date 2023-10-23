package com.bnw.beta.domain.admin.dao;

import com.bnw.beta.domain.admin.dto.NoticeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NoticeManagementDAO {
    //게시글 목록 조회
    List<NoticeDTO> noticeManagementList(@Param("page") int page, @Param("size") int size,
                               @Param("searchType") String searchType, @Param("keyword") String keyword);

    //총 게시글 개수 확인
    int noticelistCnt(@Param("searchType") String searchType, @Param("keyword") String keyword);


    //다중 삭제
    void managementDelete(List<Long> notice_no);

    //드롭다운 삭제
    void managementDelete1(String notice_no);
}
