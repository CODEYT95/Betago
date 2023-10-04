package com.bnw.beta.domain.admin.dao;

import com.bnw.beta.domain.admin.dto.NoticeDTO;
import com.bnw.beta.domain.admin.dto.NoticeFileDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NoticeDAO {
    
    //게시글 저장
    Long insert(NoticeDTO noticeDTO);

    //게시글 상세정보 조회
    NoticeDTO detail(Long notice_no);

    //게시글 수정
    void update(NoticeDTO noticeDTO);

    //게시글 목록 조회
    List<NoticeDTO> noticeList(NoticeDTO noticeDTO);


    //파일등록
    void fileUpload(NoticeFileDTO noticeFileDTO);
}
