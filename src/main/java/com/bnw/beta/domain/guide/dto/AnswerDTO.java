package com.bnw.beta.domain.guide.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnswerDTO {

    private int ans_no;
    private int qna_no;
    private String ans_content;
    private LocalDateTime ans_regdate;
    private String member_id;

}
