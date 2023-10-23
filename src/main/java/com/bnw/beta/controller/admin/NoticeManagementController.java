package com.bnw.beta.controller.admin;

import com.bnw.beta.domain.common.paging.NoticePage;
import com.bnw.beta.service.admin.notice.NoticeManagementServicelmpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class NoticeManagementController {
    private final NoticeManagementServicelmpl noticeManagementService;


    //리스트 목록 + 페이징 + 검색
    @GetMapping("/admin/noticeManage/list")
    public String noticeList(@RequestParam(value = "page", defaultValue = "1") int page,
                             @RequestParam(value = "size", defaultValue = "10") int size,
                             @RequestParam(value = "searchType", defaultValue = "all") String searchType,
                             @RequestParam(value = "keyword", defaultValue = "") String keyword,
                             Model model) {
        NoticePage noticePage = noticeManagementService.noticeManagementList(page,size,searchType,keyword);
        noticePage.setKeyword(keyword);
        noticePage.setSearchType(searchType);

        // 모든 게시물 목록을 모델에 추가
        model.addAttribute("listSize", size);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("noticePage", noticePage);
        model.addAttribute("searchType", searchType);
        model.addAttribute("keyword", keyword);
        return "admin/notice/management";
    }
    
    //다중삭제
    @PostMapping("/admin/noticeManage/delete")
    public String deleteSelectedPosts(@RequestParam("allCheck") List<Long> allCheck ) {
        noticeManagementService.delete(allCheck);
        System.out.println("DDFgd"+allCheck);
        return "redirect:/admin/noticeManage/list";
    }

    //드롭다운 삭제
    @PostMapping("/admin/noticeManage/updateStatus")
    public ResponseEntity<String> updateNoticeStatus(@RequestParam("newStatus") String newStatus) {
        try {
            //noticeManagementService.updateStatus(newStatus);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
        }
    }





}
