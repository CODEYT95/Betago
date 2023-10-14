package com.bnw.beta.controller.subscribe;

import com.bnw.beta.domain.subscribe.dto.CartDTO;
import com.bnw.beta.domain.subscribe.dto.payDTO;
import com.bnw.beta.service.subscribe.pay.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class PayController {
    @Autowired
    private PayService payService;

    @GetMapping("/cartList")
    public String cartList(Model model, @RequestParam("game_no") List<Integer> game_no) {
        List<payDTO> cartlist = payService.selectBuylist(game_no);
        model.addAttribute("cartlist", cartlist);
        System.out.println(cartlist);
        return "subscribe/pay";
    }

    @PostMapping("/payment")
    public String submitPay(@RequestParam("game_no[]") List<Integer> game_no,
                            @RequestParam("game_sell[]") List<Integer> game_sell,
                            payDTO payDTO, Principal principal) {

        String member_id = principal.getName();

        for (int i = 0; i < game_no.size(); i++) {
            Integer gameNo = game_no.get(i);
            Integer gemeSell = game_sell.get(i);
            payDTO.setMember_id(member_id);
            payDTO.setGame_no(gameNo);
            payDTO.setGame_sell(gemeSell);
            System.out.println(payDTO);
            payService.insertIntoPay(payDTO);
        }
        return "redirect:/game/list";
    }
}