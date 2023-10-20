package com.bnw.beta.domain.guide.dao;

import com.bnw.beta.domain.guide.dto.FileQuestionDTO;
import com.bnw.beta.domain.guide.dto.QuestionDTO;
import com.bnw.beta.domain.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;


import java.util.List;
@Mapper

public interface QuestionDAO {
    List<QuestionDTO> selectAllQuestions(@Param("offset") int offset);

    /*파일등록*/
    void insertFileQuestion(FileQuestionDTO fileDTO);

    int insertQuestion(QuestionDTO question);


    /*파일삭제*/
    void deleteFileQuestion(int qna_no);


    /*파일조회*/
    FileQuestionDTO selectFilesByQnaNo(int qna_no);

    QuestionDTO selectQuestionById(Integer id);

    List<QuestionDTO> getQuestionInfo(Integer id);


    MemberDTO findUserByUsername(String username);

    List<QuestionDTO> findQuestionsByMemberId(String username);

    String getPwByQnaNo(Integer id);

    void updateQuestion(QuestionDTO question);

    void deleteQuestionY(int qna_no);


    List<QuestionDTO> getQuestionsWithAnswerCount(@Param("limit") int limit, @Param("offset") int offset);

    List<QuestionDTO> findQuestionsWithAnswerCountByUser(String username);

    int countAllQuestions();

    int countQuestionsByUserId(String username);

    List<QuestionDTO> findQuestionsByUserId(@Param("userId") String userId, @Param("offset") int offset, @Param("pageSize") int pageSize);
}
