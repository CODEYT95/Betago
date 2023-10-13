package com.bnw.beta.config.vaildation.question;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AnswerForm {
    @NotEmpty(message = "댓글 내용은 필수입니다.")
    private String answer; //내용
}
