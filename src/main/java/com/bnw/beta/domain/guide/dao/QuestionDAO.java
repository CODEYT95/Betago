package com.bnw.beta.domain.guide.dao;

import com.bnw.beta.domain.guide.dto.FileQuestionDTO;
import com.bnw.beta.domain.guide.dto.QuestionDTO;
import com.bnw.beta.domain.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;
@Mapper

public interface QuestionDAO {
    List<QuestionDTO> selectAllQuestions(@Param("offset") int offset);

    /*파일등록*/
    void insertFileQuestion(FileQuestionDTO fileDTO);

    /*파일삭제*/
    FileQuestionDTO deleteFileQuestion(Long qna_no);

    /*파일조회*/
    FileQuestionDTO selectFilesByQnaNo(int qna_no);

    QuestionDTO selectQuestionById(Integer id);

    /*기존 글등록 void insertQuestion(QuestionDTO questionDTO);*/

    /*파일업로드 글등록*/
    int insertQuestion(QuestionDTO questionDTO);

    MemberDTO findUserByUsername(String username);

    String getPwByQnaNo(Integer id);

    void updateQuestion(QuestionDTO question);

    void deleteQuestion(Long qna_no);
}
