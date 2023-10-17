package com.bnw.beta.config.vaildation.member;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;


@Data
public class JoinForm {
    private String member_id;

    private String member_pw;

    private String member_repw;

    private String member_name;

    private String member_birth;

    private String member_email;

    private String member_gender;

    private String member_phone;
    private String role;

    private String member_isshow;
    private String member_tell;
    private String member_agreeM;
    private String member_agreeP;

    private String check_3;
    private String check_4;
    private String check_5;


}

