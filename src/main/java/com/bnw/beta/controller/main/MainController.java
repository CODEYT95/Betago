package com.bnw.beta.controller.main;

import com.bnw.beta.domain.admin.dto.GameDTO;
import com.bnw.beta.domain.admin.dto.NoticeDTO;
import com.bnw.beta.service.admin.game.GameService;
import com.bnw.beta.service.admin.notice.NoticeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {
    private final NoticeService noticeService;



    public  MainController(NoticeService noticeService ){

        this.noticeService = noticeService;
    }
        @GetMapping("/main")
        public String mainPage(Model model) {
            NoticeDTO noticeDTO = new NoticeDTO();
            List<NoticeDTO> noticeList = noticeService.noticeList(noticeDTO);


            model.addAttribute("noticeList", noticeList);

            return "main/main";
        }
    }

