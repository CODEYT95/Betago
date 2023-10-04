package com.bnw.beta.service.admin.game;

import com.bnw.beta.domain.admin.dao.GameDAO;
import com.bnw.beta.domain.admin.dto.GameDTO;
import lombok.RequiredArgsConstructor;
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

    /*게임콘텐츠 조회*/
   @Override
    public List<GameDTO> selectAll() {
        return gameDAO.selectAll();
    }


    //월간 (일일 단위 매출조회)
    @Override
    public List<GameDTO> selectDailySales(Date startsearch, Date endsearch)
    { return gameDAO.selectDailySales(startsearch, endsearch);}


    //년간 (월 단위 매출조회)
    @Override
    public List<GameDTO> selectMonthlySales(Date startsearch, Date endsearch)
    { return gameDAO.selectMonthlySales(startsearch, endsearch);}

}
