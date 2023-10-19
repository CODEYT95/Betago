package com.bnw.beta.service.subscribe.pay;

import com.bnw.beta.domain.subscribe.dao.payDAO;
import com.bnw.beta.domain.subscribe.dto.CartDTO;
import com.bnw.beta.domain.subscribe.dto.payDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PayServiceImpl implements PayService {
    @Autowired
    private payDAO payDAO;

    @Override
    public List<payDTO> selectContentsPay(Integer game_no) {
        return payDAO.selectContentsPay(game_no);
    }

    @Override
    public CartDTO selectCart(String member_id) {
        return payDAO.selectCart(member_id);
    }

    @Override
    public List<payDTO> selectBuylist(List<Integer> game_no) {
        return payDAO.selectBuylist(game_no);
    }

    @Override
    public int insertIntoPay(payDTO payDTO) {
        return payDAO.insertIntoPay(payDTO);
    }

    @Override
    public int insertIntoCart(int game_no, String member_id) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCart_no(game_no);
        cartDTO.setMember_id(member_id);

        return payDAO.insertIntoCart(cartDTO);
    }


    //매출
    //일단위 조회
    public List<payDTO> selectDaySales(Date pay_date){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        String startDate = dateFormat.format(pay_date);

        return payDAO.selectDaySales(startDate);
    }

    //월단위 조회
    public List<payDTO> selectMonthSales(Date pay_date, Date pay_enddate){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        String startDate = dateFormat.format(pay_date);
        String endDate = dateFormat.format(pay_enddate);


        return payDAO.selectMonthSales(startDate,endDate);
    }

    //매출 상세 조회
    public List<payDTO> selectSalesDetail(Date pay_date, Date pay_date2){
        if(pay_date2 == null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String startDate = dateFormat.format(pay_date);
            return payDAO.selectSalesDetailDay(startDate);
        }else{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
            String startDate = dateFormat.format(pay_date2);
            return payDAO.selectSalesDetailMonth(startDate);
        }

    }
}
