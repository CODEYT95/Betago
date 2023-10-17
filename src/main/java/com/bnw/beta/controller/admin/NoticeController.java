package com.bnw.beta.controller.admin;

import com.bnw.beta.domain.admin.dto.NoticeDTO;
import com.bnw.beta.domain.common.paging.NoticePage;
import com.bnw.beta.service.admin.notice.NoticeServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
public class NoticeController {
    private final NoticeServiceImpl noticeService;

    //리스트 목록 + 페이징 + 검색
    @GetMapping("/notice/list")
    public String noticeList(@RequestParam(value = "page", defaultValue = "1") int page,
                             @RequestParam(value = "size", defaultValue = "10") int size,
                             @RequestParam(value = "searchType", defaultValue = "all") String searchType,
                             @RequestParam(value = "keyword", defaultValue = "") String keyword,
                             Model model) {

        System.out.println(size);
        //상단고정 게시물
        List<NoticeDTO> topNoticeList = noticeService.getTopNoticeList();
        System.out.println("공지목록 컨트롤러"+topNoticeList);

        NoticePage noticePage = noticeService.noticeList(page, size, searchType, keyword);
        noticePage.setKeyword(keyword);
        noticePage.setSearchType(searchType);

        // 모든 게시물 목록을 모델에 추가
        model.addAttribute("allNoticeList", noticePage.getAllNoticeList());
        System.out.println(noticePage.getAllNoticeList());
        model.addAttribute("listSize", size);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("noticePage", noticePage);
        model.addAttribute("topNoticeList", topNoticeList);
        model.addAttribute("searchType", searchType);
        model.addAttribute("keyword", keyword);
        return "admin/notice/noticeList";
    }

    //공지게시판 글작성 폼
    @GetMapping("/admin/notice/write")
    public String noticeWriteForm() {

        return "admin/notice/noticeWrite";
    }

    //공지게시판 글작성 처리
    @PostMapping("/admin/notice/write")
    public String noticeWrite(@ModelAttribute NoticeDTO noticeDTO,
                              @RequestParam("file") MultipartFile[] file,
                              @RequestParam(name = "type", defaultValue = "일반") String type,
                              @RequestParam(name = "timeWrite", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date timeWrite,
                              Model model, Principal principal) throws IOException {
        noticeDTO.setMember_id(principal.getName());
        System.out.println(noticeDTO);
        try {
            System.out.println(noticeDTO);
            noticeService.insert(noticeDTO, file, type, timeWrite);
            return "redirect:/notice/list";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "글 작성 중 오류가 발생했습니다: " + e.getMessage());
            return "error";
        }
    }

    //공지게시판 상세내용
    @GetMapping("/notice/detail/{notice_no}")
    public String noticeDetail(@PathVariable("notice_no") Long notice_no, Model model) {
        NoticeDTO noticeDTO = (NoticeDTO) noticeService.detail(notice_no);
        noticeService.viewCnt(noticeDTO);
        model.addAttribute("noticeDTO", noticeDTO);
        System.out.println("컨트롤DTO=" + noticeDTO);
        return "admin/notice/noticeDetail";
    }

    //공지게시판 수정폼
    @GetMapping("/admin/edit/{notice_no}")
    public String edit(@PathVariable("notice_no") Long notice_no, Model model) {
        NoticeDTO noticeDTO = noticeService.detail(notice_no);


        model.addAttribute("notice_no", notice_no);
        model.addAttribute("noticeDTO", noticeDTO);
        System.out.println("수정컨트롤 파일" + noticeDTO);
        return "admin/notice/noticeUpdate";
    }

    //공지게시판 수정처리
    @PostMapping("/admin/notice/update")
    public String update(@RequestParam("notice_no") Long notice_no,
                         @ModelAttribute NoticeDTO noticeDTO,
                         @RequestParam("file") MultipartFile[] file,Principal principal) throws IOException {
        noticeDTO.setMember_id(principal.getName());
        noticeService.update(notice_no, noticeDTO, file);
        return "redirect:/notice/list";
    }

    //공지게시판 삭제
    @GetMapping("/admin/notice/delete/{notice_no}")
    public String delete(@PathVariable("notice_no") Long notice_no) {
        noticeService.delete(notice_no);
        return "redirect:/notice/list";
    }
}
