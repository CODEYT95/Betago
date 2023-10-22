package com.bnw.beta.controller.admin;

import com.bnw.beta.domain.admin.dto.NoticeDTO;
import com.bnw.beta.domain.common.paging.FAQPage;
import com.bnw.beta.domain.common.paging.NoticePage;
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
    public String faqList(@RequestParam(value = "page", defaultValue = "1") int page,
                          @RequestParam(value = "size", defaultValue = "5") int size,
                          @RequestParam(value = "searchType", defaultValue = "all") String searchType,
                          @RequestParam(value = "keyword", defaultValue = "") String keyword,
                          Model model){
        //리스트 불러오기
        FAQPage faqPage = faqService.faqList(page, size, searchType, keyword);
        faqPage.setKeyword(keyword);
        faqPage.setSearchType(searchType);

        model.addAttribute("faqList",1);
        model.addAttribute("currentPage", page);
        model.addAttribute("faqPage", faqPage);
        model.addAttribute("searchType", searchType);
        model.addAttribute("keyword", keyword);

        return "admin/FAQ/FAQList";
    }

    //FAQ수정
    @GetMapping("/admin/FAQ/edit/{notice_no}")
    public String edit(@PathVariable("notice_no") Long notice_no, Model model) {
        NoticeDTO noticeDTO = noticeService.detail(notice_no);

        model.addAttribute("faqList",1);
        model.addAttribute("notice_no", notice_no);
        model.addAttribute("faq", noticeDTO);
        return "admin/FAQ/FAQUpdate";
    }

    //FAQ수정처리
    @PostMapping("/admin/FAQ/update")
    public String update(@RequestParam("notice_no") Long notice_no,
                         @ModelAttribute NoticeDTO noticeDTO,
                         Principal principal) throws IOException {
        noticeDTO.setMember_name(principal.getName());
        System.out.println("faq컨트롤"+noticeDTO);
        faqService.update(noticeDTO,notice_no);
        return "redirect:/FAQ/list";
    }

    //FAQ삭제
    @GetMapping("/admin/FAQ/delete/{notice_no}")
    public String delete(@PathVariable("notice_no") Long notice_no, Model model) {
        System.out.println("FAQ삭제"+notice_no);
        model.addAttribute("faqList",1);
        faqService.delete(notice_no);
        return "redirect:/FAQ/list";
    }

}