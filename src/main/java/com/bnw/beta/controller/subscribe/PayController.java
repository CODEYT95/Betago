package com.bnw.beta.controller.subscribe;

import com.bnw.beta.domain.subscribe.dto.CartDTO;
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

    @GetMapping("/cartList")
    public String cartList(Model model, Integer game_no) {
        // 수정: selectCart 메서드를 호출해야 합니다.
        List<CartDTO> selectCartList = payService.selectCart(game_no);
        System.out.println("리스트 컨트롤러");

        // 수정: 모델에 새로운 변수명으로 저장합니다.
        model.addAttribute("selectCartList", selectCartList);
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