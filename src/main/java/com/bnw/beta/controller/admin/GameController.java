package com.bnw.beta.controller.admin;

import com.bnw.beta.domain.admin.dto.GameDTO;
import com.bnw.beta.service.admin.game.GameService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/game")
@Controller
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    //게임콘텐츠 등록

    @GetMapping("/insert")
    public String insertGame() {
        return "admin/game/gameInsert";
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
    public String selectAll(Model model) {
        List<GameDTO> gameList = gameService.selectAll();
        model.addAttribute("gameList", gameList);
        return "admin/game/gameList";
    }

    // 게임콘텐츠 제목 검색
    @GetMapping("/searchByTitle")
    public String searchByTitle(@RequestParam("game_title") String game_title, Model model) {
        if (game_title != null && !game_title.trim().isEmpty()) {
            // 사용자가 게임 제목을 선택한 경우에만 필터링
            List<GameDTO> filteredGames = gameService.searchByTitle(game_title);
            model.addAttribute("gameList", filteredGames);
        } else {
            // 선택한 게임이 없는 경우 전체 목록을 유지
            List<GameDTO> allGames = gameService.selectAll();
            model.addAttribute("gameList", allGames);
        }
        return "admin/game/gameList";
    }
}


    /*월간 (일일 단위 매출조회)
    @GetMapping("/dailyList")
    public String selectDailySales() {
        return "admin/game/gameSales";
    }

    //년간 (월 단위 매출조회)
    @GetMapping("/monthlyList")
    public String selectMonthlySales() {
        return "admin/game/gameSales";
    }

    //월별/일별 판매 건수, 판매금액 ajax로 가져오기
    @ResponseBody
    @PostMapping("/getChartDataAjax")
    public void getChartDataAjax() {
     */