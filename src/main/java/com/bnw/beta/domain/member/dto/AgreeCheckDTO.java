package com.bnw.beta.domain.member.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AgreeCheckDTO {
    private Long terms_record;
    private String member_id;
    private String agree;
    private Date agree_date;
}
