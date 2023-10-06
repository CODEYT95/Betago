package com.bnw.beta.controller.admin;

import com.bnw.beta.domain.admin.dto.NoticeDTO;
import com.bnw.beta.domain.admin.dto.NoticeFileDTO;
import com.bnw.beta.service.admin.notice.NoticeServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeServiceImpl noticeService;

    //공지게시판 글목록조회
    @GetMapping("/list")
    public String noticeList(Model model,NoticeDTO noticeDTO){
        List<NoticeDTO> noticeList = noticeService.noticeList(noticeDTO);
        model.addAttribute("noticeList",noticeList);
        System.out.println("서비스"+noticeList);
        return "admin/notice/noticeList";
    }

    //공지게시판 글작성 폼
    @GetMapping("/write")
    public String noticeWriteForm(){
        return "admin/notice/noticeWrite";
    }

    //공지게시판 글작성 처리
    @PostMapping("/write")
    public String noticeWrite(@ModelAttribute NoticeDTO noticeDTO,
                              @RequestParam("file")MultipartFile[] file,
                              Authentication authentication, Model model) throws IOException{
       String member_id = authentication.getName();

        try{
            noticeService.insert(noticeDTO, file,member_id);
            return "redirect:list";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage","글 작성 중 오류가 발생했습니다: " + e.getMessage());
            return "error";
        }

    }

    //공지게시판 상세내용
    @GetMapping("/detail/{notice_no}")
    public String noticeDetail(@PathVariable("notice_no") Long notice_no, Model model){
        NoticeDTO noticeDTO = (NoticeDTO) noticeService.detail(notice_no);
        model.addAttribute("noticeDTO",noticeDTO);
        System.out.println("컨트롤DTO="+noticeDTO);
        return "admin/notice/noticeDetail";
    }

    //공지게시판 수정폼
    @GetMapping("/edit/{notice_no}")
    public String edit(@PathVariable("notice_no") Long notice_no, Model model){
        NoticeDTO noticeDTO = noticeService.detail(notice_no);
        model.addAttribute("notice_no",notice_no);
        model.addAttribute("noticeDTO",noticeDTO);
        System.out.println("수정컨트롤 파일"+noticeDTO);
        return "admin/notice/noticeUpdate";
    }

    //공지게시판 수정처리
    @PostMapping("/update")
    public String update(@RequestParam("notice_no") Long notice_no,
                         @ModelAttribute NoticeDTO noticeDTO,
                         @RequestParam("file") MultipartFile[] file) throws IOException {
        System.out.println("컨트롤러 디티오"+ noticeDTO);
        noticeService.update(notice_no,noticeDTO,file);
        return "redirect:/notice/list";
    }
}
