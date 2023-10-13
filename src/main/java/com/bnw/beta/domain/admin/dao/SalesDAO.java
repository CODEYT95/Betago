package com.bnw.beta.domain.admin.dao;

import com.bnw.beta.domain.admin.dto.GameDTO;
import com.bnw.beta.domain.admin.dto.SalesDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface SalesDAO {
    //월간 (일일 단위 매출조회)
    List<SalesDTO> selectDailySales(Date sales_startsearch, Date sales_endsearch);

    //년간 (월 단위 매출조회)
    List<SalesDTO> selectMonthlySales(Date sales_startsearch, Date sales_endsearch);
}
