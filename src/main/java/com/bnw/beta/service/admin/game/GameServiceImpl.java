package com.bnw.beta.service.admin.game;

import com.bnw.beta.domain.admin.dao.GameDAO;
import com.bnw.beta.domain.admin.dto.GameDTO;
import com.bnw.beta.domain.admin.dto.GameFileDTO;
import com.bnw.beta.domain.subscribe.dto.payDTO;
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

    /*게임콘텐츠 조회*/
    @Override
    public List<GameDTO> selectGameList(String game_title, int limit, int offset) {
        return gameDAO.selectGameList(game_title, limit ,limit*offset);
    }

    @Override
    public int updateGame(Integer game_no) {
        return gameDAO.updateGame(game_no);
    }

    @Override
    public int gameCount(Integer game_no) {
        return gameDAO.gameCount(game_no);
    }

    //게임콘텐츠 제목 조회
    @Override
    public List<GameDTO> selectGameTitle(){
        return gameDAO.selectGameTitle();
    }

    //게임콘텐츠 갯수
    @Override
    public int countGameList(String game_title){
        return gameDAO.countGameList(game_title);
    }

    //구독하기 체크
    @Override
    public String gameBuyCheck(List<Integer> game_no, String member_id){

        GameDTO gameDTO = new GameDTO();
        gameDTO.setMember_id(member_id);

        for (Integer gameNo : game_no) {
            gameDTO.setGame_no(gameNo);

            int result = gameDAO.gameBuyCheck(gameDTO);

            if (result > 0) {
                return "reject";
            }
        }
        return "possible";
    }

    //월간 (일일 단위 매출조회)
    @Override
    public List<GameDTO> selectDailySales(Date game_startsearch, Date game_endsearch)
    { return gameDAO.selectDailySales(game_startsearch, game_endsearch);}


    //년간 (월 단위 매출조회)
    @Override
    public List<GameDTO> selectMonthlySales(Date game_startsearch, Date game_endsearch)
    { return gameDAO.selectMonthlySales(game_startsearch, game_endsearch);}

}
