package com.bnw.beta.controller.admin;

import com.bnw.beta.domain.admin.dto.GameDTO;
import com.bnw.beta.domain.admin.dto.GameFileDTO;
import com.bnw.beta.domain.learning.dto.GroupDTO;
import com.bnw.beta.domain.subscribe.dto.payDTO;
import com.bnw.beta.service.admin.game.GameService;
import com.bnw.beta.service.subscribe.pay.PayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Date;
import java.util.List;


@RequestMapping("/game")
@Controller
@RequiredArgsConstructor
public class GameController {
    private final PayService payService;
    private final GameService gameService;

    //게임콘텐츠 등록

    @GetMapping("/insert")
    public String insertGame() {
        return "admin/game/gameInsert";
    }

    @PostMapping("/gameInsert")
    public String insertGame(Principal principal, @ModelAttribute GameDTO dto, @RequestParam("imageFile") MultipartFile imageFile, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String member_id = principal.getName();
        dto.setMember_id(member_id);
        int result = gameService.insertGame(dto);

        String projectDir = System.getProperty("user.dir"); // 현재 프로젝트 디렉토리 가져오기
        Path uploadPath = Paths.get(projectDir, "src", "main", "resources", "static", "image", "guide", "game");

        if (!imageFile.isEmpty()) {
            String fileName = imageFile.getOriginalFilename();
            String filePath = String.valueOf(uploadPath.resolve(fileName));
            try {
                GameFileDTO gameFileDTO = new GameFileDTO();
                gameFileDTO.setGame_no(dto.getGame_no());
                gameFileDTO.setFilegame_name(fileName);
                gameFileDTO.setFilegame_path(filePath);
                System.out.println(gameFileDTO);

                gameService.insertGameImage(gameFileDTO);

                imageFile.transferTo(new File(filePath));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        redirectAttributes.addFlashAttribute("message", "등록이 완료되었습니다.");
        return "redirect:/game/list";
    }
    //게임콘텐츠 조회
    @GetMapping("/list")
    public String selectAll(@RequestParam(value = "game_title", defaultValue = "") String game_title,
                            @RequestParam(value = "offset", defaultValue = "0") int offset,
                            Model model) {

        int limit = 6;

        model.addAttribute("gameList", gameService.selectGameList(game_title, limit, offset));
        model.addAttribute("gameTitle", gameService.selectGameTitle());
        model.addAttribute("totalCount", gameService.countGameList(game_title));
        model.addAttribute("title",game_title);
        return "admin/game/gameList";
    }

    // 게임콘텐츠 제목 검색
    @PostMapping("/list")
    @ResponseBody
    public List<GameDTO> gameListMore(@RequestParam(value = "game_title", defaultValue = "") String game_title,
                                       @RequestParam(value = "offset", defaultValue = "0") int offset,
                                       Model model) {
        int limit = 6;
        return gameService.selectGameList(game_title, limit, offset);
    }


    //////
    @GetMapping("/salesGraph")
    public String get(@RequestParam(value = "pay_date", required = false) @DateTimeFormat(pattern = "yyyy-MM") Date pay_date,
                      @RequestParam(value = "pay_enddate", required = false) @DateTimeFormat(pattern = "yyyy-MM") Date pay_enddate,
                      Model model) {
        if (pay_date != null && pay_enddate == null) {
            model.addAttribute("comment", pay_date);
            model.addAttribute("dayList", payService.selectDaySales(pay_date));
        } else if (pay_enddate != null) {
            model.addAttribute("comment", pay_date);
            model.addAttribute("comment2", pay_enddate);
            model.addAttribute("dayList", payService.selectMonthSales(pay_date, pay_enddate));
        }
        return "admin/sales/salesGraph";
    }

    @GetMapping("/salesList")
    public String get2(@RequestParam(value = "pay_date", required = false) @DateTimeFormat(pattern = "yyyy-MM") Date pay_date,
                       @RequestParam(value = "pay_enddate", required = false) @DateTimeFormat(pattern = "yyyy-MM") Date pay_enddate,
                       Model model) {

        if (pay_date != null && pay_enddate == null) {
            model.addAttribute("comment", pay_date);
            model.addAttribute("dayList", payService.selectDaySales(pay_date));
        } else if (pay_enddate != null) {
            model.addAttribute("comment", pay_date);
            model.addAttribute("comment2", pay_enddate);
            model.addAttribute("dayList", payService.selectMonthSales(pay_date, pay_enddate));
        }
        return "admin/sales/salesList";
    }

    @PostMapping("/sales")
    @ResponseBody
    public List<payDTO> post1(@RequestParam(value = "pay_date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date pay_date,
                              @RequestParam(value = "pay_date2", required = false) @DateTimeFormat(pattern = "yyyy-MM") Date pay_date2,
                              Model model) {
        return payService.selectSalesDetail(pay_date, pay_date2);
    }
}