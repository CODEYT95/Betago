package com.bnw.beta.controller.subscribe;

import com.bnw.beta.domain.subscribe.dto.subContentsDTO;
import com.bnw.beta.service.subscribe.subContents.SubContentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

//나의 구독상품 조회
@Controller
public class SubContentsController {

    @Autowired
    private SubContentsService subContentsService;

    @GetMapping("/list")
    public String mycontentsList(@RequestParam(name = "startDate",defaultValue = "2020-08-25") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                 @RequestParam(name = "endDate",defaultValue = "2030-08-25") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                 Model model, String member_id) {

        member_id = "dumy";
        subContentsDTO subContentsDTO = new subContentsDTO();
        subContentsDTO.setMember_id(member_id);
        subContentsDTO.setStartDate(startDate);
        subContentsDTO.setEndDate(endDate);
        if (!startDate.equals(LocalDate.of(2020, 8, 25)) && !endDate.equals(LocalDate.of(2030, 8, 25))) {
            model.addAttribute("startDate",startDate);
            model.addAttribute("endDate",endDate);
        }
        List<subContentsDTO> contentsList = subContentsService.selectContents(subContentsDTO);



        System.out.println(contentsList);
        model.addAttribute("contentsList", contentsList);
        return "/subscribe/subcontents";
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
