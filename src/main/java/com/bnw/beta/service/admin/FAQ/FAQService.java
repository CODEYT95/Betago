package com.bnw.beta.service.admin.FAQ;

import com.bnw.beta.domain.admin.dto.NoticeDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FAQService {
    List<NoticeDTO> faqList();

}
