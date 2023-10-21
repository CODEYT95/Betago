package com.bnw.beta.service.admin.FAQ;

import com.bnw.beta.domain.admin.dao.FaqDAO;
import com.bnw.beta.domain.admin.dao.NoticeDAO;
import com.bnw.beta.domain.admin.dto.NoticeDTO;
import com.bnw.beta.domain.common.paging.FAQPage;
import com.bnw.beta.domain.common.paging.NoticePage;
import com.bnw.beta.service.admin.notice.NoticeServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class FAQServiceImpl implements FAQService{

    private final FaqDAO faqDAO;

    //faq 메인
    @Override
    public List<NoticeDTO> faqAll(){
        return faqDAO.faqAll();
    }

    //FAQ리스트
    @Override
    public FAQPage faqList(int pageNum, int size, String searchType, String keyword){
        int offset = (pageNum - 1) * size;
        if (pageNum <= 0) {
            pageNum = 1;
        }
        //faq리스트
        List<NoticeDTO> faqList = faqDAO.faqList(offset, size, searchType, keyword);
        System.out.println("리스트"+faqList);
        int listCnt = faqDAO.listCnt(searchType, keyword);

        FAQPage faqPage = new FAQPage(listCnt,pageNum, size,faqList ,searchType, keyword);
        faqPage.setListCnt(listCnt);
        faqPage.setFaqList(faqList);

        return faqPage;
    }

    //FAQ업데이트
    @Override
    public void update(NoticeDTO noticeDTO, Long notice_no) {
        noticeDTO.setNotice_no(notice_no);
        System.out.println("faq서비스"+noticeDTO);
        faqDAO.update(noticeDTO);
    }

    //FAQ삭제
    @Override
    public void delete(Long notice_no) {
        System.out.println("FAQ삭제"+notice_no);
        faqDAO.delete(notice_no);
    }
}
