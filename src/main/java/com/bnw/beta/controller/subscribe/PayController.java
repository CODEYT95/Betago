package com.bnw.beta.controller.subscribe;

import com.bnw.beta.domain.subscribe.dto.payDTO;
import com.bnw.beta.service.subscribe.pay.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PayController {
    @Autowired
    private PayService payService;

    @GetMapping("/paylist")
    public String payList(Model model, Integer game_no){

        List<payDTO> mypaylist = payService.selectContentsPay(game_no);
        System.out.println("리스트 컨트롤러");

        model.addAttribute("mypaylist", mypaylist);
        return "/subscribe/pay";
    }

    @PostMapping("/submit-payment")
    public String submitPay(payDTO payDTO, String member_id) {
        System.out.println("결제컨트롤러");
        int result = payService.insertIntoPay(payDTO);
        System.out.println(result);
        return "redirect:/subscribe/paylist";
    }
}