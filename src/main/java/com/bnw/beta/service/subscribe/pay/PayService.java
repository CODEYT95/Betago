package com.bnw.beta.service.subscribe.pay;

import com.bnw.beta.domain.subscribe.dto.CartDTO;
import com.bnw.beta.domain.subscribe.dto.payDTO;

import java.util.List;
//콘텐츠 구매
public interface PayService {
    List<payDTO>selectContentsPay(Integer game_no);

    List<CartDTO>selectCart(Integer game_no);
    int insertIntoPay(payDTO payDTO);
    int insertIntoCart(CartDTO cartDTO);

    List<payDTO>selectBuylist(List<Integer> game_no);

}
