package com.bnw.beta.service.guide.answer;

import com.bnw.beta.config.vaildation.question.AnswerForm;
import com.bnw.beta.domain.guide.dao.AnswerDAO;
import com.bnw.beta.domain.guide.dao.QuestionDAO;
import com.bnw.beta.domain.guide.dto.AnswerDTO;
import com.bnw.beta.domain.guide.dto.QuestionDTO;
import com.bnw.beta.domain.member.dto.MemberDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerDAO answerDAO;
    private QuestionDAO questionDAO;


    public void addAnswer(int qna_no, String answer, MemberDTO memberDTO){
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setQna_no(qna_no);
        answerDTO.setAns_content(answer);
        answerDTO.setAns_regdate(LocalDateTime.now());
        answerDTO.setMember_id(memberDTO.getMember_id());
        answerDAO.insertAnswer(answerDTO);

    }
}
