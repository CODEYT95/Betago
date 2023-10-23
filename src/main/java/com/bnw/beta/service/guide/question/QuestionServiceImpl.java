package com.bnw.beta.service.guide.question;

import com.bnw.beta.config.vaildation.question.QuestionForm;
import com.bnw.beta.domain.guide.dao.QuestionDAO;
import com.bnw.beta.domain.guide.dto.FileQuestionDTO;
import com.bnw.beta.domain.guide.dto.QuestionDTO;
import com.bnw.beta.domain.member.dto.MemberDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionDAO questionDAO;

    public List<QuestionDTO> getQuestions(int page) {
        int offset = page * 10;
        return questionDAO.selectAllQuestions(offset);
    }


    /*파일업로드 용*/
    @Transactional
    public int add(String subject, String content, String pw, MultipartFile file, MemberDTO memberDTO) {
        QuestionDTO question = new QuestionDTO();
        question.setQna_title(subject);
        question.setQna_content(content);
        question.setQna_pw(pw);
        question.setQna_regdate(LocalDateTime.now());
        question.setMember_id(memberDTO.getMember_id());

        /*int qna_no = questionDAO.insertQuestion(question);
        이거 때문에 안되고 있었음
        * */

        questionDAO.insertQuestion(question);
        int qna_no = question.getQna_no();
        System.out.println("생성된 qna_no: " + qna_no);

        // 파일이 제공되었는지 확인
        if (file != null && !file.isEmpty()) {
            System.out.println("파일업로드 진행 중");
            String storedPath = storeFile(file);  // 파일 저장
            System.out.println("파일저장완료" + file);
            FileQuestionDTO fileQuestion = new FileQuestionDTO();
            fileQuestion.setQna_no(qna_no);
            fileQuestion.setFilequ_name(file.getOriginalFilename());
            fileQuestion.setFilequ_rename(storedPath);
            fileQuestion.setFilequ_path(storedPath);
            /*주석처리 하지말아주세요*/


            questionDAO.insertFileQuestion(fileQuestion);  // 파일 정보 데이터베이스에 저장


        }
        return qna_no;
    }




    public QuestionDTO selectQuestion(Integer qna_no) {
        return questionDAO.selectQuestionById(qna_no);
    }

    public List<QuestionDTO> getQuestionInfo(Integer id){
        return questionDAO.getQuestionInfo(id);
    }

    public List<QuestionDTO> findQuestionsByUserId(String username){
       /* return questionDAO.findQuestionsByMemberId(username);*/
        return questionDAO.findQuestionsWithAnswerCountByUser(username);
    }

    public void modify(QuestionDTO question, String subject, String content, String pw, MultipartFile file) {
        question.setQna_title(subject);
        question.setQna_content(content);
        question.setQna_pw(pw);
        question.setQna_regdate(LocalDateTime.now());
        questionDAO.updateQuestion(question);
        System.out.println("Received password: " + pw);

        if (file != null && !file.isEmpty()) {
            questionDAO.deleteFileQuestion(question.getQna_no());

            String storedPath = storeFile(file);
            FileQuestionDTO fileQuestion = new FileQuestionDTO();
            fileQuestion.setQna_no(question.getQna_no());
            fileQuestion.setFilequ_name(file.getOriginalFilename());
            fileQuestion.setFilequ_rename(storedPath);
            fileQuestion.setFilequ_path(storedPath);

            // 데이터베이스에 파일 정보를 업데이트 (여기서는 새로운 정보로 추가하였지만, 필요하면 기존 정보를 수정하도록 로직을 변경해야 함)
            questionDAO.insertFileQuestion(fileQuestion);
        }
    }




    @Override
    public boolean isPasswordCorrect(Integer id, String inputPw) {
        return false;
    }

    /*public void delete(QuestionDTO question) {
        questionDAO.deleteQuestion(question.getQna_no());
    }
    */
    /*public void delete(QuestionDTO question) {
        // 먼저 파일 정보를 삭제
        questionDAO.deleteFileQuestion(question.getQna_no());
        // 그 다음 질문 정보 삭제
        questionDAO.deleteQuestion(question.getQna_no());

    }*/

    public void deleteY(QuestionDTO question){
    questionDAO.deleteQuestionY(question.getQna_no());
    }

    /*파일삭제*/


    public String storeFile(MultipartFile file) {
        String projectDir = System.getProperty("user.dir"); // 현재 프로젝트 디렉토리 가져오기
        Path uploadPath = Paths.get(projectDir, "src", "main", "resources", "static", "image", "guide", "question");
        String originalFilename = file.getOriginalFilename();
        String storedPath = uploadPath + "/" + originalFilename;

        try {
            file.transferTo(new File(storedPath));
            return storedPath;
        } catch (Exception e) {
            throw new RuntimeException("Failed to store the file.", e);
        }
    }

    public List<QuestionDTO> getQuestionsWithAnswerCount(int page) {
        int limit = 10; // 페이지 당 표시할 항목 수
        /*int offset = limit * page;*/
        /*int offset = (page > 0) ? 10 * (page - 1) : 0;*/
        int offset = limit * (page - 1);
        return questionDAO.getQuestionsWithAnswerCount(limit, offset);
    }

    public List<QuestionDTO> findQuestionsByUserId(String username, int page, int pageSize) {
        // 페이지 계산 (일반적으로 페이지는 1부터 시작하지만, 데이터베이스 offset은 0부터 시작)
        int offset = (page - 1) * pageSize;
        return questionDAO.findQuestionsByUserId(username, offset, pageSize);
    }

    public int countQuestionsByUserId(String username){
        return questionDAO.countQuestionsByUserId(username);
    };

    public int getTotalQuestionsCount() {
        return questionDAO.countAllQuestions();
    }

   /* public QuestionDTO getQuestion(Integer id) {
        return questionDAO.getQuestionById(id);
    }
*/



}
