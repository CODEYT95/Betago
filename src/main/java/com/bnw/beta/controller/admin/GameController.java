package com.bnw.beta.controller.admin;

import com.bnw.beta.domain.admin.dto.GameDTO;
import com.bnw.beta.service.admin.game.GameService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/game")
@Controller
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;


    //게임콘텐츠 등록

    @GetMapping("/insert")
    public String insertGame(){
        return "operate/game/gameInsert";
    }
    @PostMapping("/gameInsert")
    public String insertGame(@ModelAttribute GameDTO dto, HttpServletRequest request) {
        String member_id = "dumy";
        dto.setMember_id(member_id);
        int result = gameService.insertGame(dto);
        System.out.println(result);
        return "redirect:/game/list";
    }

    //게임콘텐츠 조회
    @GetMapping("/list")
    public String selectAll() {
        return "admin/game/gameList";

    }

    //월간 (일일 단위 매출조회)
    @GetMapping("/dailyList")
    public String selectDailySales() {
        return "admin/game/gameSales";
    }

    //년간 (월 단위 매출조회)
    @GetMapping("/monthlyList")
    public String selectMonthlySales() {
        return "admin/game/gameSales";
    }

}
