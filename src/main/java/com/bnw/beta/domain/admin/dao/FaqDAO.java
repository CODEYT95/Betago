package com.bnw.beta.domain.admin.dao;

import com.bnw.beta.domain.admin.dto.NoticeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FaqDAO {
    List<NoticeDTO> faqList();

    //게시글 수정
    int update(NoticeDTO noticeDTO);

    //게시글 삭제
    void delete(Long notice_no);
}
