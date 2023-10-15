package com.bnw.beta.controller.admin;

import com.bnw.beta.domain.admin.dto.NoticeDTO;
import com.bnw.beta.service.admin.FAQ.FAQServiceImpl;
import com.bnw.beta.service.admin.notice.NoticeServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class FAQController {
    private final FAQServiceImpl faqService;
    private final NoticeServiceImpl noticeService;

    //FAQ 리스트
    @GetMapping("/FAQ/list")
    public String faqList(Model model){
        List<NoticeDTO> faqList = faqService.faqList();
        System.out.println("dddd"+ faqList);
        model.addAttribute("faqList",faqList);
        return "admin/FAQ/FAQList";
    }

    //FAQ수정
    @GetMapping("/admin/FAQ/edit/{notice_no}")
    public String edit(@PathVariable("notice_no") Long notice_no, Model model) {
        NoticeDTO noticeDTO = noticeService.detail(notice_no);

        model.addAttribute("notice_no", notice_no);
        model.addAttribute("faq", noticeDTO);
        return "admin/FAQ/FAQUpdate";
    }

    //FAQ수정처리
    @PostMapping("/admin/FAQ/update")
    public String update(@RequestParam("notice_no") Long notice_no,
                         @ModelAttribute NoticeDTO noticeDTO,
                         Principal principal) throws IOException {
        noticeDTO.setMember_id(principal.getName());
        faqService.update(noticeDTO,notice_no);
        return "admin/FAQ/FAQList";
    }

    //FAQ삭제
    //공지게시판 삭제
    @GetMapping("/admin/FAQ/delete/{notice_no}")
    public String delete(@PathVariable("notice_no") Long notice_no) {
        faqService.delete(notice_no);
        return "admin/FAQ/FAQList";
    }

}