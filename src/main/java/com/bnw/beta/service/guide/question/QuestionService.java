package com.bnw.beta.service.guide.question;

import com.bnw.beta.config.vaildation.question.QuestionForm;
import com.bnw.beta.domain.guide.dto.QuestionDTO;
import com.bnw.beta.domain.member.dto.MemberDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface QuestionService {

    List<QuestionDTO> getQuestions(int page);

    QuestionDTO selectQuestion(Integer qna_no);

    List<QuestionDTO> getQuestionInfo(Integer id);

    /*기존꺼
    void add(String subject, String content, MemberDTO memberDTO);*/
    int add(String subject, String content, String pw, MultipartFile file, MemberDTO memberDTO);


    void deleteY(QuestionDTO question);

    public String storeFile(MultipartFile file);

    void modify(QuestionDTO question, String subject, String content,String pw, MultipartFile file);


    boolean isPasswordCorrect(Integer id, String inputPw);


    List<QuestionDTO> findQuestionsByUserId(String username);

    /*페이징+답변갯수 표시*/
    List<QuestionDTO> getQuestionsWithAnswerCount(int page);

    int getTotalQuestionsCount();

    List<QuestionDTO>  findQuestionsByUserId(String username, int page, int pageSize);


    int countQuestionsByUserId(String username);
}
