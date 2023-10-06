package com.bnw.beta.domain.guide.dao;

import com.bnw.beta.domain.guide.dto.QuestionDTO;
import com.bnw.beta.domain.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;
@Mapper

public interface QuestionDAO {
    List<QuestionDTO> selectAllQuestions(@Param("offset") int offset);


    QuestionDTO selectQuestionById(Integer id);

    void insertQuestion(QuestionDTO questionDTO);

    MemberDTO findUserByUsername(String username);
}
