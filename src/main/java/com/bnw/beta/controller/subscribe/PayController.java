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

import java.util.List;

@Controller
public class PayController {
    @Autowired
    private PayService payService;

    /*
    @PostMapping(value = "/addCart")
    public String addToCart(@RequestParam("selectedGameNos") int[] selectedGameNos, String member_id) {
        member_id="dumy";
        StringBuilder redirectUrl = new StringBuilder("redirect:/cartList?");
        for (int gameNo : selectedGameNos) {
            redirectUrl.append("GameNos=").append(gameNo);

            payService.insertIntoCart(gameNo, member_id);
        }

        // "/cartList"로 바로 리다이렉트
        return "redirect:/cartList";
    }
*/
    //제가 한 컨트롤러
    @GetMapping("/cartList")
    public String cartList(Model model, @RequestParam("game_no") List<Integer> game_no) {
        List<payDTO> cartlist = payService.selectBuylist(game_no);
        model.addAttribute("cartlist", cartlist);
        return "subscribe/pay";
    }

    @PostMapping("/payment")
    public String submitPay(@RequestParam("game_no[]") List<Integer> game_no,
                            payDTO payDTO, String member_id) {

        member_id="baduk";

        for (int i = 0; i < game_no.size(); i++) {
            Integer gameNo = game_no.get(i);

            payDTO.setMember_id(member_id);
            payDTO.setGame_no(gameNo);

            System.out.println(payDTO);
            payService.insertIntoPay(payDTO);
        }
        return "redirect:/subscribe/paylist";
    }
}