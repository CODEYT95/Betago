package com.bnw.beta.controller.admin;

import com.bnw.beta.domain.admin.dto.NoticeDTO;
import com.bnw.beta.domain.common.paging.NoticePage;
import com.bnw.beta.service.admin.notice.NoticeServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeServiceImpl noticeService;
    //리스트 목록 + 페이징 + 검색
    @GetMapping("/list")
    public String noticeList(@RequestParam(value = "page", defaultValue = "1") int page,
                             @RequestParam(value = "size", defaultValue = "10") int size,
                             @RequestParam(value = "searchType", defaultValue = "all") String searchType,
                             @RequestParam(value = "keyword", defaultValue = "") String keyword,
                             Model model) {
        int listCount = noticeService.listCnt(searchType, keyword);

        int totalPages = (int) Math.ceil((double) listCount / size);
        page = Math.min(Math.max(1, page), totalPages);
        List<NoticeDTO> noticeList = noticeService.noticeList(page, size, searchType, keyword);
        NoticePage noticePage = new NoticePage(listCount, page, size, noticeList);

        noticePage.setKeyword(keyword);
        noticePage.setSearchType(searchType);

        model.addAttribute("currentPage", page);
        model.addAttribute("listCount", listCount);
        model.addAttribute("noticePage", noticePage);
        model.addAttribute("searchType",searchType);
        model.addAttribute("keyword",keyword);
        return "admin/notice/noticeList";
    }


    //공지게시판 글작성 폼
    @GetMapping("/write")
    public String noticeWriteForm() {
        return "admin/notice/noticeWrite";
    }

    //공지게시판 글작성 처리
    @PostMapping("/write")
    public String noticeWrite(@ModelAttribute NoticeDTO noticeDTO,
                              @RequestParam("file") MultipartFile[] file,
                              Authentication authentication, Model model) throws IOException {
        String member_id = authentication.getName();
        model.addAttribute("member_id", member_id);
        try {
            noticeService.insert(noticeDTO, file, member_id);
            return "redirect:list";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "글 작성 중 오류가 발생했습니다: " + e.getMessage());
            return "error";
        }

    }

    //공지게시판 상세내용
    @GetMapping("/detail/{notice_no}")
    public String noticeDetail(@PathVariable("notice_no") Long notice_no, Model model) {
        NoticeDTO noticeDTO = (NoticeDTO) noticeService.detail(notice_no);
        model.addAttribute("noticeDTO", noticeDTO);
        System.out.println("컨트롤DTO=" + noticeDTO);
        return "admin/notice/noticeDetail";
    }

    //공지게시판 수정폼
    @GetMapping("/edit/{notice_no}")
    public String edit(@PathVariable("notice_no") Long notice_no, Model model) {
        NoticeDTO noticeDTO = noticeService.detail(notice_no);
        model.addAttribute("notice_no", notice_no);
        model.addAttribute("noticeDTO", noticeDTO);
        System.out.println("수정컨트롤 파일" + noticeDTO);
        return "admin/notice/noticeUpdate";
    }

    //공지게시판 수정처리
    @PostMapping("/update")
    public String update(@RequestParam("notice_no") Long notice_no,
                         @ModelAttribute NoticeDTO noticeDTO,
                         @RequestParam("file") MultipartFile[] file) throws IOException {
        System.out.println("컨트롤러 디티오" + noticeDTO);
        noticeService.update(notice_no, noticeDTO, file);
        return "redirect:/notice/list";
    }

    //공지게시판 삭제
    @GetMapping("/delete/{notice_no}")
    public String delete(@PathVariable("notice_no") Long notice_no) {
        noticeService.delete(notice_no);
        return "redirect:/notice/list";
    }
}
