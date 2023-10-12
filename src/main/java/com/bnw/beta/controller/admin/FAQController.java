package com.bnw.beta.controller.admin;

import com.bnw.beta.domain.admin.dto.NoticeDTO;
import com.bnw.beta.service.admin.FAQ.FAQServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/FAQ")
public class FAQController {
    private final FAQServiceImpl faqService;
    @GetMapping("/list")
    public String faqList(Model model){
        List<NoticeDTO> faqList = faqService.faqList();
        System.out.println("dddd"+ faqList);
        model.addAttribute("faqList",faqList);
        return "admin/FAQ/FAQList";
    }

}