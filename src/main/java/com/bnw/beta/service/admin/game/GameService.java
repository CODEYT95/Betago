package com.bnw.beta.service.admin.game;


import com.bnw.beta.domain.admin.dto.GameDTO;
import com.bnw.beta.domain.admin.dto.GameFileDTO;
import com.bnw.beta.domain.subscribe.dto.payDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface GameService {

    //게임콘텐츠 등록
    public int insertGame(GameDTO dto);

    //게임콘텐츠 썸네일 등록
    int insertGameImage(GameFileDTO gameFileDTO);

    //게임콘텐츠 조회
    List<GameDTO> selectAll();

    //게임콘텐츠 조회
    List<GameDTO> selectGameList(String game_title, int limit, int offset);
    int updateGame(Integer game_no);

    int gameCount(Integer game_no);

    //게임콘텐츠 제목 조회
    List<GameDTO> selectGameTitle();

    //게임콘텐츠 갯수
    int countGameList(String game_title);

    //구독하기 체크
    String gameBuyCheck(List<Integer> game_no, String member_id);

    //월간 (일일 단위 매출조회)
    public List<GameDTO> selectDailySales(Date game_startsearch, Date game_endsearch);

    //년간 (월 단위 매출조회)
    public List<GameDTO> selectMonthlySales(Date game_startsearch, Date game_endsearch);
}
