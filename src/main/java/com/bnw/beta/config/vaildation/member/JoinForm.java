package com.bnw.beta.config.vaildation.member;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class JoinForm {
    @NotEmpty(message = "ID를 입력해주세요.")
    @Pattern(regexp = "^[a-z0-9]{4,20}$", message = "아이디는 영어 소문자와 숫자만 사용하여 4~20자리여야 합니다.")
    private String member_id;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$", message = "비밀번호는 8~16자리수여야 합니다. 영문 대소문자, 숫자, 특수문자를 1개 이상 포함해야 합니다.")
    private String member_pw;

    private String member_repw;

    @NotEmpty(message = "이름을 입력해주세요.")
    private String member_name;

    @NotEmpty(message = "생년월일을 입력해주세요.")
    private String member_birth;

    @NotEmpty(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
    private String member_email;

    @NotEmpty(message = "성별을 선택해주세요.")
    private String member_gender;

    @NotEmpty(message = "핸드폰 번호를 입력해주세요.")
    private String member_phone;

    @NotEmpty(message = "회원유형을 선택해주세요.")
    private String role;

    private String member_isshow;
    private String member_tell;
    private String member_agreeM;
    private String member_agreeP;

    private String check_3;
    private String check_4;
    private String check_5;


}

