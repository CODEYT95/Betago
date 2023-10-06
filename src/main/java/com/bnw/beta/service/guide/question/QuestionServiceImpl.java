package com.bnw.beta.service.guide.question;

import com.bnw.beta.domain.guide.dao.QuestionDAO;
import com.bnw.beta.domain.guide.dto.QuestionDTO;
import com.bnw.beta.domain.member.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class QuestionServiceImpl {
        @Autowired
        private QuestionDAO questionDAO;

        public List<QuestionDTO> getQuestions(int page) {
            int offset = page * 10;
            return questionDAO.selectAllQuestions(offset);
        }

    public void add(String subject, String content, String pw, MemberDTO memberDTO){
        QuestionDTO question = new QuestionDTO();
        question.setQna_title(subject);
        question.setQna_content(content);
        question.setQna_pw(pw);
        question.setQna_regdate(LocalDateTime.now());
        question.setMember_id(memberDTO.getMember_id());
        questionDAO.insertQuestion(question);
    }

    public QuestionDTO selectQuestion(Integer qna_no) {
        return questionDAO.selectQuestionById(qna_no);
    }

    public void modify(QuestionDTO question, String subject, String content,String pw) {
    question.setQna_title(subject);
    question.setQna_content(content);
    question.setQna_pw(pw);
    question.setQna_regdate(LocalDateTime.now());
    questionDAO.updateQuestion(question);
    }


   /* public QuestionDTO getQuestion(Integer id) {
        return questionDAO.getQuestionById(id);
    }
*/

}
