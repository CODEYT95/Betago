package com.bnw.beta.service.admin.FAQ;

import com.bnw.beta.domain.admin.dto.NoticeDTO;
import com.bnw.beta.domain.common.paging.FAQPage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FAQService {

    //faq 메인
    List<NoticeDTO> faqAll();

    FAQPage faqList(@Param("page") int page, @Param("size") int size, @Param("searchType") String searchType, @Param("keyword") String keyword);

    //수정
    void update(NoticeDTO noticeDTO, Long notice_no);
    
    //삭제
    void delete(Long notice_no);
}
