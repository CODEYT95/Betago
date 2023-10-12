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

    CartDTO selectCart(String member_id);
    int insertIntoPay(payDTO payDTO);
    int insertIntoCart(CartDTO cartDTO);

    //제가 추가한 DAO
    List<CartDTO>selectCart(Integer game_no);
    List<payDTO>selectBuylist(List<Integer> game_no);

}