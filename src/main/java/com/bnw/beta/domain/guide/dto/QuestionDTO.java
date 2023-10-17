package com.bnw.beta.domain.guide.dto;

import lombok.Data;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class QuestionDTO {

    private int qna_no;
    private String qna_title;
    private String qna_content;
    private String qna_isshow;
    private String qna_pw;
    private String member_id;
    private LocalDateTime qna_regdate;

    private int answerCount;
    private PageRequest pageable;


    private List<FileQuestionDTO> questionfiles;
    /*주석하지말아주세요*/

    private List<AnswerDTO> answerList;

    //댓글 컬럼
    private int ans_no;
    private String ans_content;
    private LocalDateTime ans_regdate;
}
