package com.bnw.beta.service.admin.game;

import com.bnw.beta.domain.admin.dao.GameDAO;
import com.bnw.beta.domain.admin.dto.GameDTO;
import com.bnw.beta.domain.admin.dto.GameFileDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameDAO gameDAO;
    /*게임콘텐츠 등록*/
    @Override
    public int insertGame(GameDTO dto) {
        return gameDAO.insertGame(dto);
    }

     //게임콘텐츠 썸네일 등록
    @Override
    public int insertGameImage(GameFileDTO gameFileDTO){return gameDAO.insertGameImage(gameFileDTO);}

    /*게임콘텐츠 조회*/
   @Override
    public List<GameDTO> selectAll() {
        return gameDAO.selectAll();
    }


    //월간 (일일 단위 매출조회)
    @Override
    public List<GameDTO> selectDailySales(Date game_startsearch, Date game_endsearch)
    { return gameDAO.selectDailySales(game_startsearch, game_endsearch);}


    //년간 (월 단위 매출조회)
    @Override
    public List<GameDTO> selectMonthlySales(Date game_startsearch, Date game_endsearch)
    { return gameDAO.selectMonthlySales(game_startsearch, game_endsearch);}

    //게임콘텐츠 제목검색
    public List<GameDTO> searchByTitle(@Param("game_title") String game_title)
    {return gameDAO.searchByTitle(game_title);}

}
