package com.bnw.beta.config.vaildation.question;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class QuestionForm {
    @NotBlank(message = "제목은 필수 입력사항입니다.")
    @Size(max=200) //Question Entity에서 @Colum(length=200)에 맞춰
    private String subject;//제목

    //내용
    @NotEmpty(message = "내용은 필수 입력사항입니다.")
    private String content;//내용
}
