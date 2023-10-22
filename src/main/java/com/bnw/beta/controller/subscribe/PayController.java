package com.bnw.beta.controller.subscribe;

import com.bnw.beta.domain.member.dao.MemberDAO;
import com.bnw.beta.domain.subscribe.dto.payDTO;
import com.bnw.beta.service.member.MemberService;
import com.bnw.beta.service.subscribe.pay.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("pay")
public class PayController {
    @Autowired
    private PayService payService;
    @Autowired
    private MemberService memberService;

    @GetMapping("/cartList")
    public String cartList(Model model, @RequestParam("game_no") List<Integer> game_no) {
        List<payDTO> cartlist = payService.selectBuylist(game_no);
        System.out.println("test"+cartlist);
        model.addAttribute("cartlist", cartlist);
        return "subscribe/pay";
    }

    @PostMapping("/payment")
    public String submitPay(@RequestParam("game_no[]") List<Integer> game_no,
                            @RequestParam("game_date[]") List<String> game_date,
                            @RequestParam("pay_type") String pay_type,
                            payDTO payDTO, Principal principal) {

        String member_id = principal.getName();

        for (int i = 0; i < game_no.size(); i++) {
            Integer gameNo = game_no.get(i);
            String gameDate = game_date.get(i);
            payDTO.setMember_id(member_id);
            payDTO.setGame_no(gameNo);
            payDTO.setGame_date(gameDate);
            System.out.println(payDTO);
            payService.insertIntoPay(payDTO);
        }
        if(!game_no.isEmpty()){
            memberService.updateLicense(member_id);
        }

        return "redirect:/game/list";
    }
}
