package com.bnw.beta.domain.guide.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuestionDTO {

    private Long qna_no;
    private String qna_title;
    private String qna_content;
    private String qna_isshow;
    private String qna_pw;
    private String member_id;
    private LocalDateTime qna_regdate;

}
