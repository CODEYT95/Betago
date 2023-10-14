package com.bnw.beta.service.admin.game;


import com.bnw.beta.domain.admin.dto.GameDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface GameService {

    //게임콘텐츠 등록
    public int insertGame(GameDTO dto);

    //게임콘텐츠 조회
   public List<GameDTO> selectAll();

    //월간 (일일 단위 매출조회)
    public List<GameDTO> selectDailySales(Date game_startsearch, Date game_endsearch);

    //년간 (월 단위 매출조회)
    public List<GameDTO> selectMonthlySales(Date game_startsearch, Date game_endsearch);

    //게임콘텐츠 제목검색
    public List<GameDTO> searchByTitle(@Param("game_title") String game_title);
}
