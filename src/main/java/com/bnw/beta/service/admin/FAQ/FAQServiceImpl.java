package com.bnw.beta.service.admin.FAQ;

import com.bnw.beta.domain.admin.dao.FaqDAO;
import com.bnw.beta.domain.admin.dao.NoticeDAO;
import com.bnw.beta.domain.admin.dto.NoticeDTO;
import com.bnw.beta.service.admin.notice.NoticeServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FAQServiceImpl implements FAQService{

    private final FaqDAO faqDAO;

    @Override
    public List<NoticeDTO> faqList(){
        return faqDAO.faqList();
    }
}
