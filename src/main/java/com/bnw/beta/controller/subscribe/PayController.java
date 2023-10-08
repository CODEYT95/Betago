package com.bnw.beta.controller.subscribe;

import com.bnw.beta.domain.subscribe.dto.CartDTO;
import com.bnw.beta.domain.subscribe.dto.payDTO;
import com.bnw.beta.service.subscribe.pay.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PayController {
    @Autowired
    private PayService payService;

    @PostMapping(value = "/addCart")
    public String addToCart(@RequestParam("selectedGameNos") int[] selectedGameNos, String member_id) {
        member_id="dumy";
        StringBuilder redirectUrl = new StringBuilder("redirect:/cartList?");
        for (int gameNo : selectedGameNos) {
            redirectUrl.append("selectedGameNos=").append(gameNo).append("&");

            payService.insertIntoCart(gameNo, member_id);
        }

        // "/cartList"로 바로 리다이렉트
        return "redirect:/cartList";
    }

    @GetMapping("/cartList")
    public String cartList(Model model, String member_id, @RequestParam(value = "selectedGameNos", required = false) int[] selectedGameNos) {
        // 장바구니 목록을 조회합니다.
        member_id="dumy";
        CartDTO cartList = payService.selectCart(member_id);

        // 조회한 장바구니 목록을 Model에 추가
        model.addAttribute("cartList", cartList);

        // 선택한 게임 번호를 Model에 추가
        model.addAttribute("selectedGameNos", selectedGameNos);

        // pay 페이지로 이동
        return "subscribe/pay";
    }


    @PostMapping("/payment")
    public String submitPay(payDTO payDTO, String member_id) {
        System.out.println("결제컨트롤러");
        int result = payService.insertIntoPay(payDTO);
        System.out.println(result);
        return "redirect:/subscribe/paylist";
    }
}