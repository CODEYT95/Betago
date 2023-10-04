package com.bnw.beta.domain.member.dto;

import lombok.*;

@Data
public class MemberDTO {
    private Long member_no;
    private String member_id;
    private String member_pw;
    private String member_name;
    private String member_birth;
    private String member_email;
    private String member_gender;
    private String member_phone;
    private String member_tell;
    private String member_agreeM;
    private String member_agreeP;
    private String member_isshow;
    private String role;
}

