package com.bnw.beta.domain.subscribe.dao;

import com.bnw.beta.domain.subscribe.dto.CartDTO;
import com.bnw.beta.domain.subscribe.dto.payDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
//콘텐츠 구매
@Mapper
@Repository
public interface payDAO {
    List<payDTO>selectContentsPay(Integer game_no);

    List<CartDTO>selectCart(Integer game_no);

    List<payDTO>selectBuylist(List<Integer> game_no);

    int insertIntoPay(payDTO payDTO);
    int insertIntoCart(CartDTO cartDTO);
}