package com.bnw.beta.domain.admin.dao;

import com.bnw.beta.domain.admin.dto.GameDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface GameDAO {
    //게임콘텐츠 등록
    int insertGame(GameDTO dto);

    //게임콘텐츠 조회
    List<GameDTO> selectAll();

    //월간 (일일 단위 매출조회)
    List<GameDTO> selectDailySales(Date startsearch, Date endsearch);

    //년간 (월 단위 매출조회)
    List<GameDTO> selectMonthlySales(Date startsearch, Date endsearch);


}
