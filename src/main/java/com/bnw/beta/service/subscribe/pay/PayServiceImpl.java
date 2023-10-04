package com.bnw.beta.service.subscribe.pay;

import com.bnw.beta.domain.subscribe.dao.payDAO;
import com.bnw.beta.domain.subscribe.dto.payDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        public int insertIntoPay(payDTO payDTO) {
            return payDAO.insertIntoPay(payDTO);
        }
}
