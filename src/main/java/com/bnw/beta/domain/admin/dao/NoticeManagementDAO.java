package com.bnw.beta.domain.admin.dao;

import com.bnw.beta.domain.admin.dto.NoticeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
    void managementDelete(List<Integer> notice_no);

    // N에서 Y로 변경
    int updateStatus(@Param("notice_isshow") String notice_isshow, @Param("notice_no") Long notice_no);

    //카테고리 변경
    int updateCategory(@Param("notice_category") String notice_category, @Param("notice_no") Long notice_no);

    //드롭다운으로 전체공개, 상단, 일반 변경
    int updateType(@Param("type") String type, @Param("notice_no") Long notice_no);

    //드롭다운으로 예약일 변경
    int updateReservation(@Param("notice_reservation") LocalDate notice_reservation, @Param("notice_no") Long notice_no);


}
