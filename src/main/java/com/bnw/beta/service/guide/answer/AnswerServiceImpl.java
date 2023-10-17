package com.bnw.beta.service.guide.answer;

import com.bnw.beta.config.vaildation.question.AnswerForm;
import com.bnw.beta.config.vaildation.question.AnswerNotFoundException;
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

    @Override
    public AnswerDTO getAnswer(int qns_no) {
        // 데이터베이스에서 해당 ID의 댓글을 가져옵니다.
        return answerDAO.getAnswerById(qns_no);

    }

    /*사용자 아이디 가져오기*/
    public AnswerDTO findAnswerById(int ans_no){
        return answerDAO.findAnswerById(ans_no);
    }

    /*댓글삭제*/
    public void deleteAnswer(int ans_no) {
        answerDAO.deleteAnswerById(ans_no);
    }

    public AnswerDTO selectAnswer(int ans_no){
        return answerDAO.selectAnswerById(ans_no);
    }

    public void modifyAnswer(int ans_no, String answer) {
        AnswerDTO answerDTO = answerDAO.selectAnswerById(ans_no);
        if (answerDTO == null) {
            throw new AnswerNotFoundException("해당 번호에는 답글이 없다");
        }
        answerDTO.setAns_content(answer);
        answerDTO.setAns_regdate(LocalDateTime.now());
        answerDAO.modifyAnswer(answerDTO);
    }

   /* @Override
    public void modify(AnswerDTO answerDTO) {
        // 데이터베이스에 저장된 댓글을 업데이트합니다.
        answerDAO.updateAnswer(answerDTO); // 이 메서드는 매퍼에서 정의해야 합니다.
    }*/

    public void addAnswer(int qna_no, String answer, MemberDTO memberDTO){
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setQna_no(qna_no);
        answerDTO.setAns_content(answer);
        answerDTO.setAns_regdate(LocalDateTime.now());
        answerDTO.setMember_id(memberDTO.getMember_id());
        answerDAO.insertAnswer(answerDTO);

    }
}
