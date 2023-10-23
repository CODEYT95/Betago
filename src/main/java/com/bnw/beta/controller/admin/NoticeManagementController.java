package com.bnw.beta.controller.admin;

import com.bnw.beta.domain.admin.dto.NoticeDTO;
import com.bnw.beta.domain.admin.dto.NoticeManagementDTO;
import com.bnw.beta.domain.common.paging.NoticePage;
import com.bnw.beta.service.admin.notice.NoticeManagementServicelmpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    @PostMapping ("/admin/noticeManage/delete")
    public String deleteSelectedPosts(@RequestParam("allCheck") List<Integer> allCheck) {
        System.out.println("DDFgd"+allCheck);
        noticeManagementService.delete(allCheck);
        return "redirect:/admin/noticeManage/list";
    }

    // 드롭다운 N/Y변경
    @PostMapping("/admin/updateStatus")
    @ResponseBody
    public String updateStatus(@RequestParam("notice_isshow") String notice_isshow, @RequestParam("notice_no") Long notice_no) {
        System.out.println("컨트롤"+notice_isshow);
        System.out.println("컨트롤"+notice_no);
        return noticeManagementService.updateStatus(notice_isshow,notice_no);

    }

    //드롭다운으로 카테고리 변경
    @PostMapping("/admin/updateCategory")
    @ResponseBody
    public String updateCategory(@RequestParam("notice_category") String notice_category, @RequestParam("notice_no") Long notice_no) {
        System.out.println("컨트롤"+notice_category);
        System.out.println("컨트롤"+notice_no);
        return noticeManagementService.updateCategory(notice_category,notice_no);

    }
    //드롭다운으로 타입 변경
    @PostMapping("/admin/updateType")
    @ResponseBody
    public String updateType(@RequestParam("type") String type, @RequestParam("notice_no") Long notice_no) {
        System.out.println("컨트롤"+type);
        System.out.println("컨트롤"+notice_no);
        return noticeManagementService.updateType(type,notice_no);

    }

    //드롭다운으로 예약일 변경
    @PostMapping("/admin/updateReservation")
    @ResponseBody
    public String updateReservation(@RequestParam("notice_reservation") LocalDate notice_reservation, @RequestParam("notice_no") Long notice_no) {
        System.out.println("컨트롤"+notice_reservation);
        System.out.println("컨트롤"+notice_no);
        return noticeManagementService.updateReservation(notice_reservation,notice_no);

    }







}
