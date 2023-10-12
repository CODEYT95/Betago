package com.bnw.beta.service.guide.question;

import com.bnw.beta.config.vaildation.question.QuestionForm;
import com.bnw.beta.domain.guide.dao.QuestionDAO;
import com.bnw.beta.domain.guide.dto.FileQuestionDTO;
import com.bnw.beta.domain.guide.dto.QuestionDTO;
import com.bnw.beta.domain.member.dto.MemberDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService{
    @Autowired
    private QuestionDAO questionDAO;

    public List<QuestionDTO> getQuestions(int page) {
        int offset = page * 10;
        return questionDAO.selectAllQuestions(offset);
    }


  /*  기존 글등록
    public void add(String subject, String content, String pw, MemberDTO memberDTO){
        QuestionDTO question = new QuestionDTO();
        question.setQna_title(subject);
        question.setQna_content(content);
        question.setQna_pw(pw);
        question.setQna_regdate(LocalDateTime.now());
        question.setMember_id(memberDTO.getMember_id());
        questionDAO.insertQuestion(question);
    }
*/

    /*파일업로드 용*/
    public void add(String subject, String content, String pw, MultipartFile file, MemberDTO memberDTO){
        QuestionDTO question = new QuestionDTO();
        question.setQna_title(subject);
        question.setQna_content(content);
        question.setQna_pw(pw);
        question.setQna_regdate(LocalDateTime.now());
        question.setMember_id(memberDTO.getMember_id());

        int qna_no = questionDAO.insertQuestion(question);

        // 파일이 제공되었는지 확인
        if(file != null && !file.isEmpty()) {
            System.out.println("파일업로드 진행 중");
            String storedPath = storeFile(file);  // 파일 저장
            System.out.println("파일저장완료"+file);
            FileQuestionDTO fileQuestion = new FileQuestionDTO();
//            fileQuestion.setQna_no(qna_no);
//            fileQuestion.setFilequ_name(file.getOriginalFilename());
//            fileQuestion.setFilequ_rename(storedPath);
//            fileQuestion.setFilequ_path(storedPath);


            questionDAO.insertFileQuestion(fileQuestion);  // 파일 정보 데이터베이스에 저장
        }
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

    @Override
    public boolean isPasswordCorrect(Integer id, String inputPw) {
        return false;
    }

    public void delete(QuestionDTO question) {
        questionDAO.deleteQuestion(question.getQna_no());
    }


    /*이미지 파일 업로드*/
    private final String UPLOAD_DIR = "C:/uploadfile/question_img/";

    public String storeFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String storedPath = UPLOAD_DIR + "/" + originalFilename;

        try {
            file.transferTo(new File(storedPath));
            return storedPath;
        } catch (Exception e) {
            throw new RuntimeException("Failed to store the file.", e);
        }
    }

   /* public QuestionDTO getQuestion(Integer id) {
        return questionDAO.getQuestionById(id);
    }
*/

}
