package com.bnw.beta.service.guide.question;

import com.bnw.beta.domain.guide.dto.QuestionDTO;
import com.bnw.beta.domain.member.dto.MemberDTO;

import java.util.List;

public interface QuestionService {

    List<QuestionDTO> getQuestions(int page);

    QuestionDTO selectQuestion(Integer qna_no);

    void add(String subject, String content, MemberDTO memberDTO);

    void modify(QuestionDTO question, String subject, String content,String pw);

    boolean isPasswordCorrect(Integer id, String inputPw);


}
