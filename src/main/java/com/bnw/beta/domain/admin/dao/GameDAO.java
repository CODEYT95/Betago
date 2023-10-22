package com.bnw.beta.domain.admin.dao;

import com.bnw.beta.domain.admin.dto.GameDTO;
import com.bnw.beta.domain.admin.dto.GameFileDTO;
import com.bnw.beta.domain.subscribe.dto.payDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.swing.tree.RowMapper;
import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface GameDAO {

    List<GameDTO> selectAll();

    //게임콘텐츠 등록
    int insertGame(GameDTO dto);

    //게임콘텐츠 썸네일 등록
    int insertGameImage(GameFileDTO gameFileDTO);

    //게임콘텐츠 조회
    List<GameDTO> selectGameList(@Param("game_title") String game_title,@Param("limit") int limit,@Param("offset") int offset);

    int updateGame(Integer game_no);

    int gameCount(Integer game_no);

    //게임콘텐츠 제목 조회
    List<GameDTO> selectGameTitle();

    //게임콘텐츠 갯수
    int countGameList(String game_title);

    //구독하기 체크
    int gameBuyCheck(GameDTO gameDTO);

    //월간 (일일 단위 매출조회)
    List<GameDTO> selectDailySales(@Param("game_startsearch") Date game_startsearch, @Param("game_endsearch") Date game_endsearch);

    //년간 (월 단위 매출조회)
    List<GameDTO> selectMonthlySales(@Param("game_startsearch")Date game_startsearch, @Param("game_endsearch")Date game_endsearch);


}
