package com.bnw.beta.service.subscribe.pay;

import com.bnw.beta.domain.subscribe.dto.CartDTO;
import com.bnw.beta.domain.subscribe.dto.payDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
//콘텐츠 구매
public interface PayService {
    List<payDTO>selectContentsPay(Integer game_no);

    CartDTO selectCart(String member_id);
    int insertIntoPay(payDTO payDTO);
    int insertIntoCart(int game_no, String member_id);

    List<payDTO>selectBuylist(List<Integer> game_no);

    //매출
    //일단위 조회
    List<payDTO> selectDaySales(Date pay_date);

    //월단위 조회
    List<payDTO> selectMonthSales(Date pay_date, Date pay_enddate);

    //매출 상세 조회
    List<payDTO> selectSalesDetail(Date pay_date, Date pay_date2);


}
