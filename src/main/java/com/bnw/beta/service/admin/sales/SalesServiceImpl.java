/*
package com.bnw.beta.service.admin.sales;

import com.bnw.beta.domain.admin.dao.SalesDAO;
import com.bnw.beta.domain.admin.dto.SalesDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesServiceImpl implements SalesService{

    private final SalesDAO salesDAO;

    //월간 (일일 단위 매출조회)
    @Override
    public List<SalesDTO> selectDailySales(Date sales_startsearch, Date sales_endsearch)
    { return salesDAO.selectDailySales(sales_startsearch, sales_endsearch);}


    //년간 (월 단위 매출조회)
    @Override
    public List<SalesDTO> selectMonthlySales(Date sales_startsearch, Date sales_endsearch)
    { return salesDAO.selectMonthlySales(sales_startsearch, sales_endsearch);}

}
*/
