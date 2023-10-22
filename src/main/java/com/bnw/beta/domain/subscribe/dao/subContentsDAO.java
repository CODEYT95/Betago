package com.bnw.beta.domain.subscribe.dao;

import com.bnw.beta.domain.subscribe.dto.subContentsDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Mapper
@Repository
public interface subContentsDAO {
    List<subContentsDTO> selectContents(subContentsDTO subContentsDTO);

    int contentsCnt(subContentsDTO subContentsDTO);
    List<subContentsDTO> selectContentsByDate(@Param("member_id") String member_id,
                                              @Param("startDate") LocalDate  startDate,
                                              @Param("endDate") LocalDate endDate);
    Integer deleteContents(int game_no);
}
