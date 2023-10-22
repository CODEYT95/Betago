package com.bnw.beta.controller.subscribe;

import com.bnw.beta.domain.subscribe.dto.subContentsDTO;
import com.bnw.beta.service.subscribe.subContents.SubContentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

//나의 구독상품 조회
@Controller
public class SubContentsController {

    @Autowired
    private SubContentsService subContentsService;

    @GetMapping("/list")
    public String mycontentsList(@RequestParam(name = "startDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                 @RequestParam(name = "endDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                 @RequestParam(name = "offset",defaultValue = "0") int offset,
                                 Model model, Principal principal) {

        int limit = 6;

        String member_id = principal.getName();

        subContentsDTO subContentsDTO = new subContentsDTO();
        subContentsDTO.setMember_id(member_id);
        Date pay_date = new Date();


        java.sql.Date sqlStartDate = startDate != null ? new java.sql.Date(startDate.getTime()) : null;
        java.sql.Date sqlEndDate = endDate != null ? new java.sql.Date(endDate.getTime()) : null;
        subContentsDTO.setPay_date(sqlStartDate);
        subContentsDTO.setEndDate(sqlEndDate);

        subContentsDTO.setLimit(limit);
        subContentsDTO.setOffset(limit*offset);

        model.addAttribute("totalCount", subContentsService.contentsCnt(subContentsDTO));
        model.addAttribute("pay_date", sqlStartDate);
        model.addAttribute("endDate", sqlEndDate);
        List<subContentsDTO> contentsList = subContentsService.selectContents(subContentsDTO);
        model.addAttribute("contentsList", contentsList);
        return "subscribe/subContents";
    }

    @PostMapping("/list")
    @ResponseBody
    public List<subContentsDTO>listAdd (@RequestParam(name = "startDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                         @RequestParam(name = "endDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                         @RequestParam(name = "offset",defaultValue = "0") int offset,Principal principal){


        int limit = 6;

        String member_id = principal.getName();

        subContentsDTO subContentsDTO = new subContentsDTO();
        subContentsDTO.setMember_id(member_id);
        Date pay_date = new Date();


        java.sql.Date sqlStartDate = startDate != null ? new java.sql.Date(startDate.getTime()) : null;
        java.sql.Date sqlEndDate = endDate != null ? new java.sql.Date(endDate.getTime()) : null;
        subContentsDTO.setPay_date(sqlStartDate);
        subContentsDTO.setEndDate(sqlEndDate);

        subContentsDTO.setLimit(limit);
        subContentsDTO.setOffset(limit*offset);

        return subContentsService.selectContents(subContentsDTO);
    }

    @PostMapping("/deleteContents")
    public String deleteContents(@RequestParam("selectedGameNos") String selectedGameNos) {
        // 쉼표(,)로 구분된 문자열을 배열로 분리
        String[] gameNosArray = selectedGameNos.split(",");

        for (String gameNoStr : gameNosArray) {
            try {
                // 각 문자열을 정수로 변환
                int gameNo = Integer.parseInt(gameNoStr.trim());
                subContentsService.deleteContents(gameNo);
                System.out.println("success delete");
            } catch (NumberFormatException e) {
                // 정수로 변환할 수 없는 경우에 대한 예외 처리
                System.err.println("Invalid game number: " + gameNoStr);
            }
        }
        return "redirect:/list";
    }
}
