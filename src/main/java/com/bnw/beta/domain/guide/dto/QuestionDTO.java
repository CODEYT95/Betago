package com.bnw.beta.domain.guide.dto;

import lombok.Data;

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

    private List<FileQuestionDTO> questionfiles;


}
