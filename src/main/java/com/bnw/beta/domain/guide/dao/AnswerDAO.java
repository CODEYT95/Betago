package com.bnw.beta.domain.guide.dao;

import com.bnw.beta.domain.guide.dto.AnswerDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AnswerDAO {

    void insertAnswer(AnswerDTO answerDTO);
}
