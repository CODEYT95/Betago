package com.bnw.beta.service.guide.answer;

import com.bnw.beta.config.vaildation.question.AnswerForm;
import com.bnw.beta.domain.guide.dto.QuestionDTO;
import com.bnw.beta.domain.member.dto.MemberDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface AnswerService {
    void addAnswer(int qna_no, String answer,  MemberDTO memberD);
}
