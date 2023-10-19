package com.bnw.beta.domain.subscribe.dao;

import com.bnw.beta.domain.subscribe.dto.CartDTO;
import com.bnw.beta.domain.subscribe.dto.payDTO;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
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

    //매출
    //일단위 조회
    List<payDTO> selectDaySales(String startDate);

    //월단위 조회
    List<payDTO> selectMonthSales(@Param("startDate") String pay_date, @Param("endDate") String pay_enddate);

    //매출 상세 조회
    List<payDTO> selectSalesDetailDay(@Param("pay_Date") String pay_date);
    List<payDTO> selectSalesDetailMonth(@Param("pay_Date") String pay_date);

}