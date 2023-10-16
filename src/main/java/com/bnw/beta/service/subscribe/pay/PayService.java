package com.bnw.beta.service.subscribe.pay;

import com.bnw.beta.domain.subscribe.dto.CartDTO;
import com.bnw.beta.domain.subscribe.dto.payDTO;

import java.util.List;
//콘텐츠 구매
public interface PayService {
    List<payDTO>selectContentsPay(Integer game_no);

    CartDTO selectCart(String member_id);
    int insertIntoPay(payDTO payDTO);
    int insertIntoCart(int game_no, String member_id);

    List<payDTO>selectBuylist(List<Integer> game_no);

}