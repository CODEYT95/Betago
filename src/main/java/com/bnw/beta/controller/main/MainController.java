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
    private final GameService gameService;  // 게임 서비스를 추가합니다.

    // 생성자에서 GameService도 주입받습니다.
    public MainController(NoticeService noticeService, GameService gameService) {
        this.noticeService = noticeService;
        this.gameService = gameService;
    }

    @GetMapping("/main")
    public String mainPage(Model model) {
        NoticeDTO noticeDTO = new NoticeDTO();


        List<GameDTO> gameList = gameService.selectAll();
        gameList = gameList.subList(0, Math.min(gameList.size(), 6));
        model.addAttribute("gameList", gameList);

        return "main/main";
    }
}

