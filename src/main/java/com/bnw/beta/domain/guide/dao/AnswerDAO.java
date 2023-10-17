package com.bnw.beta.domain.guide.dao;

import com.bnw.beta.domain.guide.dto.AnswerDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AnswerDAO {

    void insertAnswer(AnswerDTO answerDTO);

    AnswerDTO selectAnswerById(int ans_no); // 댓글 조회

    AnswerDTO getAnswerById(int ans_no); //댓글 조회 이걸로?

    AnswerDTO findAnswerById(int ans_no); //댓글 작성자 조회

    void modifyAnswer(AnswerDTO answerDTO);  //댓글 수정

    void deleteAnswerById(int ansNo);
}
